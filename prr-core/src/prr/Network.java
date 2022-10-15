package prr;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import prr.clients.Client;
import prr.clients.ClientType;
import prr.exceptions.BadEntryException;
import prr.exceptions.DuplicateClientKeyException;
import prr.exceptions.DuplicateTerminalKeyException;
import prr.exceptions.IllegalEntryException;
import prr.exceptions.ImportFileException;
import prr.exceptions.InvalidTerminalKeyException;
import prr.exceptions.UnknownClientKeyException;
import prr.exceptions.UnknownTerminalKeyException;
import prr.exceptions.UnrecognizedEntryException;
import prr.terminals.TerminalState;
import prr.terminals.BasicTerminal;
import prr.terminals.BusyTerminalState;
import prr.terminals.FancyTerminal;
import prr.terminals.OffTerminalState;
import prr.terminals.OnTerminalState;
import prr.terminals.SilentTerminalState;
import prr.terminals.Terminal;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Class Store implements a network.
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
	public void setDirty() {
		_dirtyFlag = true;
	}

	/**
	 * Mark Network data as clean
	 */
	public void setClean() {
		_dirtyFlag = false;
	}

    /**
     * 
     * @param key
     * @return
     * @throws UnknownClientKeyException
     */
    public Client getClientByKey(String key) throws UnknownClientKeyException {
        Client c = _clients.get(key);
        if(c == null)
            throw new UnknownClientKeyException(key);
        return c;
    }

    /**
     * 
     * @param key
     * @return
     * @throws UnknownTerminalKeyException
     */
    public Terminal getTerminalByKey(String key) throws UnknownTerminalKeyException {
        Terminal t = _terminals.get(key);
        if(t == null) 
            throw new UnknownTerminalKeyException(key);
        return t;
    }

    /**
     * 
     * @return
     */
	public Collection<Client> getAllClients() {
		return _clients.values();
	}

    /**
     * 
     * @return
     */
    public Collection<Terminal> getAllTerminals() {
        return _terminals.values();
    }

    /**
     * 
     * @param c
     */
    public void addClient(Client c) {
        _clients.put(c.getKey(), c);
    }

    /**
     * 
     * @param t
     */
    public void addTerminal(Terminal t) {
        _terminals.put(t.getKey(), t);
    }

    /**
     * Registers a Client in the Network 
     * 
     * @param key Client's identifying key
     * @param name Client's name
     * @param taxID Client's tax ID
     * 
     * @throws DuplicateClientKeyException if a Client with given key already
     *                                     exists in the Network
     */
	public void registerClient(String key, String name, Integer taxId) throws 
                                                DuplicateClientKeyException {
		if(_clients.containsKey(key))
			throw new DuplicateClientKeyException(key);
        addClient(new Client(key, name, taxId));
	}

    /**
     * @param key
     * @param ownerKey
     * @param state
     * 
     * @throws InvalidTerminalKeyException if given key is not a 6 digit string
     * @throws DuplicateTerminalKeyException if a Terminal with given key already
     *                                       exists in the Network
     * @throws UnknownClientKeyException if a Client with given key doesn't exist
     *                                   in the Network
     */
    public void registerTerminal(String key, String type, String ownerKey) throws 
                                                InvalidTerminalKeyException, 
                                                    DuplicateTerminalKeyException, 
                                                        UnknownClientKeyException {
        // validate that key has only 6 digits (\d <=> [0,9] regex) 
        if(!key.matches("\\d{6}"))
            throw new InvalidTerminalKeyException(key);

        // check if Terminal with given key exists
        if(_terminals.containsKey(key))
            throw new DuplicateTerminalKeyException(key);

        // add Terminal to the Network
        addTerminal(type.equals("BASIC") ?
            new BasicTerminal(key, getClientByKey(ownerKey)) :
                new FancyTerminal(key, getClientByKey(ownerKey)));
    }


	/**
	 * Read text input file and create corresponding domain entities.
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
            // shouldn't happen
			e.printStackTrace();;
		}
    }

	/**
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
     * @param fields An array with each field for the Client entity
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
     * @param fields An array with each field for the Terminal entity
     * 
     * @throws BadEntryException
     * @throws IllegalEntryException if entry has values that do not respect the Network
     *                               integrity constraints
     * @throws BanEntryException if the entry doesn't have the correct fields
     *                           for Terminal 
     */
    public void importTerminal(String[] fields) throws 
                                            UnrecognizedEntryException, 
                                                BadEntryException, 
                                                    IllegalEntryException {
        // check for BadEntryException
        if(fields.length != 4)
            throw new BadEntryException(String.join("|", fields));
        
        // determine the terminal type
        TerminalState state = switch(fields[3]) {
        case "ON"     -> new OnTerminalState();
        case "SILENT" -> new SilentTerminalState();
        case "BUSY"   -> new BusyTerminalState();
        case "OFF"    -> new OffTerminalState();
        default -> 
            throw new UnrecognizedEntryException(fields[3]);
        };

        try {
            registerTerminal(fields[1], fields[0], fields[2]);
        } catch (InvalidTerminalKeyException 
                | DuplicateTerminalKeyException 
                | UnknownClientKeyException e) {
            throw new IllegalEntryException(String.join("|", fields));
        } finally {
            // set TerminalState
            try {
                getTerminalByKey(fields[1]).setTerminalState(state);
            } catch(UnknownTerminalKeyException e) {
                // this should never happen
            }
        }
    }

    /**
     * Parses and imports a list of friends of a Terminal
     * <p>
     * Entry format:
     * {@code FRIENDS|terminal-key|friend1-key,friend2-key...friendN-key}
     * @param fields
     */
    public void importFriends(String[] fields) throws BadEntryException,
                                                    IllegalEntryException {
        if(fields.length != 3)
            throw new BadEntryException(String.join("|", fields));

        String[] friends = fields[3].split(",");
        // add all friends to terminal
        try {
            Terminal t = getTerminalByKey(fields[1]);
            for(String s : friends)
                t.addFriend(getTerminalByKey(s));
        } catch (UnknownTerminalKeyException e) {
            throw new IllegalEntryException(e.getKey());
        }
    }
}

