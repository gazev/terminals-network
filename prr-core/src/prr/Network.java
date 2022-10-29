package prr;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import prr.clients.Client;
import prr.clients.ClientType;
import prr.terminals.TerminalState;
import prr.terminals.BasicTerminal;
import prr.terminals.BusyTerminalState;
import prr.terminals.FancyTerminal;
import prr.terminals.OffTerminalState;
import prr.terminals.OnTerminalState;
import prr.terminals.SilentTerminalState;
import prr.terminals.Terminal;
import prr.communications.Communication;
import prr.exceptions.BadEntryException;
import prr.exceptions.DuplicateClientKeyException;
import prr.exceptions.DuplicateTerminalKeyException;
import prr.exceptions.IllegalEntryException;
import prr.exceptions.ImportFileException;
import prr.exceptions.InvalidTerminalKeyException;
import prr.exceptions.SameTerminalStateException;
import prr.exceptions.UnknownClientKeyException;
import prr.exceptions.UnknownTerminalKeyException;
import prr.exceptions.UnrecognizedEntryException;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Class Network implements a Network of terminals that can
 * communicate with each other 
 */
public class Network implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	/** Signals if Network has dirty data */
	private boolean _dirtyFlag = false;

	/** Map containing Clients of this Network */
	private Map<String, Client> _clients = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

	/** Map containing Terminals of this Network */
	private Map<String, Terminal> _terminals = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /** List will all Communications of the Network */
    private List<Communication> _communications = new ArrayList<>();

	/**
	 *
	 * @return true if Network data is dirty
	 */
	public boolean isDirty() {
		return _dirtyFlag;
	}

	/**
	 * Mark Network data as dirty
     */
	public void setDirty() { _dirtyFlag = true; }

	/**
	 * Mark Network data as clean
	 */
	public void setClean() { _dirtyFlag = false; }

    /**
     * Adds given Client to the Network
     *
     * @param client Client to be added to the Network
     */
    public void addClient(Client client) {
        _clients.put(client.getKey(), client);
        setDirty();
    }


    /**
     * Add given Terminal to the Network
     *
     * @param Terminal to be added to the Network
     */
    public void addTerminal(Terminal terminal) {
        _terminals.put(terminal.getKey(), terminal);
        // add this terminal to Client's list
        terminal.getOwner().addTerminal(terminal);
        setDirty();
    }

    public Double getGlobalBalance() {
        Double sum = 0.0;
        for(Client c : getAllClients()) {
            sum += c.getClientPaidBalance() - c.getClientDebtBalance(); 
        }

        return sum;
    }

    /**********************
     *  CLIENTS
     *********************/ 
    
    /**
     * Returns a Collection with all Clients registered in the Network
     *
     * @return Collection of all Clients
     */
	public Collection<Client> getAllClients() { return _clients.values(); }


    /**
     * Returns Client with given key
     *
     * @param key Client's key
     * @return Client with given key
     *
     * @throws UnknownClientKeyException if Client with specified key doesn't
     *                                   exist in the Network
     */
    public Client getClientByKey(String key) throws UnknownClientKeyException {
        Client c = _clients.get(key);
        if(c == null)
            throw new UnknownClientKeyException(key);
        return c;
    }

    /**
     * Registers a Client into the Network with the specified attributes
     *
     * @param key Client's identifying key
     * @param name Client's name
     * @param taxId Client's tax ID
     *
     * @throws DuplicateClientKeyException if a Client with given key already
     *                                     exists in the Network
     */
	public void registerClient(String key, String name, Integer taxId) throws
                                                DuplicateClientKeyException {
        // check if Client with given key already exists
		if(_clients.containsKey(key))
			throw new DuplicateClientKeyException(key);
        addClient(new Client(key, name, taxId));
	}

    /**********************
     * TERMINALS 
     *********************/ 

    /**
     * Returns a Collection with all Terminals registered in the Network
     *
     * @return Collection of all Terminals
     */
    public Collection<Terminal> getAllTerminals() { return _terminals.values(); }

    /**
     * Returns Terminal with given key
     *
     * @param key Terminal's key
     * @return Terminal with specified key
     *
     * @throws UnknownTerminalKeyException if Terminal with specified key doesn't
     *                                     exist in the Network
     */
    public Terminal getTerminalByKey(String key) throws UnknownTerminalKeyException {
        Terminal t = _terminals.get(key);
        if(t == null)
            throw new UnknownTerminalKeyException(key);
        return t;
    }

    /**
     * Registers a Terminal in the Network with specified attributes
     *
     * @param key The identifying key of the Terminal
     * @param type The type of the Terminal, "BASIC" or "FANCY"
     * @param ownerKey The identifying key of this Terminal's owner
     *
     * @throws InvalidTerminalKeyException if given Terminal's key is not a 6
     *                                     digit string
     * @throws DuplicateTerminalKeyException if a Terminal with given key already
     *                                       exists in the Network
     * @throws UnknownClientKeyException if a Client with given key doesn't exist
     *                                   in the Network
     */
    public void registerTerminal(String terminalKey, String type, String ownerKey) throws
                                                InvalidTerminalKeyException,
                                                    DuplicateTerminalKeyException,
                                                        UnknownClientKeyException {
        // check the key has only 6 digits (\d <=> [0,9] regex)
        if(!terminalKey.matches("\\d{6}"))
            throw new InvalidTerminalKeyException(terminalKey);

        // check if Terminal with given key already exists
        if(_terminals.containsKey(terminalKey))
            throw new DuplicateTerminalKeyException(terminalKey);

        // add Terminal to the Network
        addTerminal(type.equals("BASIC") ?
            new BasicTerminal(terminalKey, getClientByKey(ownerKey)) :
                new FancyTerminal(terminalKey, getClientByKey(ownerKey)));
    }


    /*******************
     * LOOKUPS 
     *****************/

    /**
     * 
     * 
     * @return Stream of Terminals that haven't started or received any
     *         communications
     */
	public Collection<Terminal> getUnusedTerminals() {
		List<Terminal> termAux = new ArrayList<>();
		for(Terminal t : _terminals.values()){
			if(t.getReceivedCommunications().size() == 0 &&
					            t.getStartedCommunications().size() == 0 ){
					termAux.add(t);
			}
		}
		return termAux;
	}

    public Collection<Terminal> getTerminalsWithPositiveBalance() {
        List<Terminal> termAux = new ArrayList<>();
        for(Terminal t : _terminals.values()) {
            if(t.getPaidBalance() > t.getDebtBalance())
                termAux.add(t);
        }
    return termAux;
    }

    public Collection<Communication> getAllCommunications() {
        List<Communication> comms = new ArrayList<>();
        for(Client c : _clients.values()) {
            for(Terminal t : c.getTerminals()) {
                comms.addAll(t.getStartedCommunications());
            }
        }
        Collections.sort(comms);

        return comms;
    }
    
    public Collection<Communication> getCommunicationsStartedByClient(Client client) {
        List<Communication> comms = new ArrayList<>();
        for(Terminal t : client.getTerminals()) {
            comms.addAll(t.getStartedCommunications());
        }
        Collections.sort(comms);

        return comms;
    }

    public Collection<Communication> getCommunicationsReceivedByClient(Client client) {
        List<Communication> comms = new ArrayList<>();
        for(Terminal t : client.getTerminals()) {
            comms.addAll(t.getReceivedCommunications());
        }
        Collections.sort(comms);

        return comms;
    }


    /**********************+
     * IMPORTS 
     ************************/

	/**
	 * Reads a text input file and creates corresponding Network entities.
	 *
	 * @param filename name of the text input file
     *
	 * @throws UnrecognizedEntryException if a specified entity doesn't exist
     *                                    or isn't recognized by the program
     * @throws BadEntryException if entry doesn't provide the correct fields for
     *                           an entity
     * @throws IllegalEntryException if imported entity violates Network
     *                               integrity constraints
     * @throws IOException if there is an IO erro while processing the text file
	 */
	void importFile(String filename) throws UnrecognizedEntryException,
                                                BadEntryException,
                                                    IllegalEntryException,
                                                        IOException {
		try {
			FileReader f = new FileReader(filename);
			BufferedReader b = new BufferedReader(f);
            String inputLine;
            while((inputLine = b.readLine()) != null)
                parseInputLine(inputLine);
			b.close();
		} catch (IOException e) {
			e.printStackTrace();;
		}
    }


	/**
     * Parses a line from a text file describing an entity in the Network
     * 
	 * @param inputLine line to be parsed
     *
     * @throws UnrecognizedEntryException if a specified entity doesn't exist
     *                                    or isn't recognized by the program
     * @throws BadEntryException if entry doesn't provide the correct fields for
     *                           an entity
     * @throws IllegalEntryException if imported entity violates Network
     *                               integrity constraints
	 */
	public void parseInputLine(String inputLine) throws
                                                UnrecognizedEntryException,
                                                    BadEntryException,
                                                        IllegalEntryException {
		String[] fields = inputLine.split("\\|");
        switch (fields[0]) {
            case "CLIENT"         -> importClient(fields);
            case "BASIC", "FANCY" -> importTerminal(fields);
            case "FRIENDS"        -> importFriends(fields);
            default ->
               throw new UnrecognizedEntryException(fields[0]);
        }
	}


    /**
     * Parses and imports a Client entity.
     * <p>
     * Format of Client entity input:
     * {@code CLIENT|key|name|taxID}
     *
     * @param fields An array with each field required to import a Client
     *
     * @throws BadEntryException if the entry doesn't have the correct number
     *                           of fields for Client
     * @throws IllegalEntryException if Client key is duplicate
     */
    public void importClient(String[] fields) throws BadEntryException,
                                            IllegalEntryException {
        // check for correct number of fields for Client
        if(fields.length != 4)
            throw new BadEntryException(String.join("|", fields));

        try {
            registerClient(fields[1], fields[2], Integer.parseInt(fields[3]));
        } catch (DuplicateClientKeyException e) {
            throw new IllegalEntryException(fields[1]);
        }
    }


    /**
     * Parses and imports a Terminal entity.
     * <p>
     * Format of Terminal entity input:
     * {@code terminal-type|terminal-key|owner-key|state}
     *
     * @param fields An array with each field required to import a Terminal
     *
     * @throws UnrecognizedEntryException if a specified entity doesn't exist
     *                                    or isn't recognized by the program
     * @throws BadEntryException if the entry doesn't have the correct number of
     *                           fields for Terminal
     * @throws IllegalEntryException if entry has a field that does not respect
     *                               the Network's integrity constraints
     */
    public void importTerminal(String[] fields) throws
                                            UnrecognizedEntryException,
                                                BadEntryException,
                                                    IllegalEntryException {
        // check entry is correct
        if(fields.length != 4)
            throw new BadEntryException(String.join("|", fields));

        // determine the terminal state
        TerminalState state = switch(fields[3]) {
        case "ON"     -> new OnTerminalState();
        case "SILENCE" -> new SilentTerminalState();
        case "BUSY"   -> new BusyTerminalState();
        case "OFF"    -> new OffTerminalState();
        default ->
            throw new UnrecognizedEntryException(fields[3]);
        };

        try {
            registerTerminalFromImport(fields[1], fields[0], fields[2], state);
        } catch (InvalidTerminalKeyException
                | DuplicateTerminalKeyException
                | UnknownClientKeyException e) {
            throw new IllegalEntryException(String.join("|", fields));
        }
    }


    /**
     * Parses and imports a list of Terminal's friends' keys
     * <p>
     * Entry format:
     * {@code FRIENDS|terminal-key|friend1-key,friend2-key...friendN-key}
     *
     * @param fields An array with each field required to import
     *               Terminal's friends
     *
     * @throws BadEntryException if entry doesn't have at least two fields
     * @throws IllegalEntryException if the key of the Terminal or any of its
     *                               friends doesn't exist
     */
    public void importFriends(String[] fields) throws BadEntryException,
                                                    IllegalEntryException {
        // check entry is good
        if(fields.length < 3)
            throw new BadEntryException(String.join("|", fields));

        String[] friends = fields[2].split(",");
        // add all friends to terminal
        try {
            Terminal t = getTerminalByKey(fields[1]);
            for(String s : friends)
                t.addFriend(s, this);
        } catch (UnknownTerminalKeyException e) {
            throw new IllegalEntryException(e.getKey());
        }
    }


    public void registerTerminalFromImport(String terminalKey, String type, String ownerKey, 
                                                TerminalState state) throws
                                                    InvalidTerminalKeyException,
                                                        DuplicateTerminalKeyException,
                                                            UnknownClientKeyException {
        // check the key has only 6 digits (\d <=> [0,9] regex)
        if(!terminalKey.matches("\\d{6}"))
            throw new InvalidTerminalKeyException(terminalKey);

        // check if Terminal with given key already exists
        if(_terminals.containsKey(terminalKey))
            throw new DuplicateTerminalKeyException(terminalKey);

        // add Terminal to the Network
        addTerminal(type.equals("BASIC") ?
            new BasicTerminal(terminalKey, getClientByKey(ownerKey), state) :
                new FancyTerminal(terminalKey, getClientByKey(ownerKey), state));
    }
}
