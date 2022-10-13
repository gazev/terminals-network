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
import prr.exceptions.DuplicateClientKeyException;
import prr.exceptions.ImportFileException;
import prr.exceptions.UnrecognizedEntryException;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Class Store implements a network.
 */
public class Network implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	/** Signals Network has dirty data */
	private boolean _dirtyFlag = false;

	private boolean _wtf = true;

	/** TreeMap containing Clients of this Network */
	private Map<String, Client> _clients = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

	/** TreeMap containing Terminals of this Network */
	private Map<String, Client> _terminals = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

	/**
	 * 
	 * @return true if Network data is dirty 
	 */
	public boolean isDirty() {
		return _dirtyFlag;
	}

	/**
	 * Signal Network data is dirty
	 */
	public void setDirty() {
		_dirtyFlag = true;
	}

	/**
	 * Signal Network data is clean
	 */
	public void setClean() {
		_dirtyFlag = false;
	}

	public Collection<Client> getAllClients() {
		return _clients.values();
	}

	public void registerClient(String key, String name, Integer taxId) 
			throws DuplicateClientKeyException {
		if(_clients.containsKey(key))
			throw new DuplicateClientKeyException(name);
		_clients.put(key, new Client(key, name, taxId));
	}



	public void parseInput(String inputLine) throws UnrecognizedEntryException{
		String[] fields = inputLine.split("\\|");
		switch (fields[0]) {
			case "CLIENT":
				registerClient(fields[1], fields[2], Integer.parseInt(fields[3]));
			case "FRIENDS":
			;
			default:
			;
		} 
	}

	public void parseImportLine(String line) {
	}


	/**
	 * Read text input file and create corresponding domain entities.
	 *
	 * @param filename name of the text input file
         * @throws UnrecognizedEntryException if some entry is not correct
	 * @throws IOException if there is an IO erro while processing the text file
	 */
	void importFile(String filename) throws UnrecognizedEntryException, IOException  {
		try {
			FileReader f = new FileReader(filename);
			BufferedReader b = new BufferedReader(f);
			for(String inputLine = b.readLine(); inputLine != null; inputLine = b.readLine()) {
				parseInput(inputLine);
			}
			b.close();
		} catch (IOException e) {
			throw new IOException();
		}
    }
}

