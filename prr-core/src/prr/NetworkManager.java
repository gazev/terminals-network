package prr;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import prr.exceptions.BadEntryException;
import prr.exceptions.IllegalEntryException;
import prr.exceptions.ImportFileException;
import prr.exceptions.MissingFileAssociationException;
import prr.exceptions.UnavailableFileException;
import prr.exceptions.UnrecognizedEntryException;

/**
 * Manage access to network and implement load/save operations.
 */
public class NetworkManager {

	/** The network itself. */
	private Network _network = new Network();

	/** The name of the file associated with current Network instance */
	private String _filename = "";

	/**
	 * 
	 * @return the current network instance
	 */
    public Network getNetwork() {
		return _network;
	}

	/**
	 * @param filename name of the file containing the serialized application's state
     *                 to load.
	 * @throws UnavailableFileException if the specified file does not exist or there is
     *                                  an error while processing this file.
	 */
	public void load(String filename) throws UnavailableFileException {
		try {
			FileInputStream f = new FileInputStream(filename);
			ObjectInputStream o = new ObjectInputStream(f);
			_network = (Network) o.readObject();
			o.close();
		} catch(IOException | ClassNotFoundException e) {
			throw new UnavailableFileException(filename);
		}
		_filename = filename;
	}

	/**
     * Saves the serialized application's state into the file associated to the current network.
     *
	 * @throws FileNotFoundException if for some reason the file cannot be created or opened. 
	 * @throws MissingFileAssociationException if the current network does not have a file.
	 * @throws IOException if there is some error while serializing the state of the network to disk.
	 */
	public void save() throws FileNotFoundException, MissingFileAssociationException, IOException {
		// no file associated with this instance of the application
		if(_filename == null || _filename.isBlank())
			throw new MissingFileAssociationException();

		// write Network object to file
		if(_network.isDirty()) {
			FileOutputStream f = new FileOutputStream(_filename);
			BufferedOutputStream b = new BufferedOutputStream(f);
			ObjectOutput o = new ObjectOutputStream(b);
			o.writeObject(_network);
			o.close();
			// after save, Network data is not dirty
			_network.setClean();
		}
	}

	/**
     * Saves the serialized application's state into the specified file. This file becomes
	 * associated with the current Network instance 
     *
	 * @param filename the name of the file
	 * 
	 * @throws FileNotFoundException if for some reason the file cannot be 
	 *                               created or opened.
	 * @throws MissingFileAssociationException if the current network does not 
	 *                                         have a file.
	 * @throws IOException if there is some error while serializing the state 
	 *                     of the network to disk.
	 */
	public void saveAs(String filename) throws FileNotFoundException,
													MissingFileAssociationException,
														IOException {
		_filename = filename;
		save();
	}

	/**
	 * Read text input file and create domain entities
	 * 
	 * @param filename name of the text input file
	 * 
	 * @throws ImportFileException if there are problems with input entries
	 *                             e.g unknown entity specified, wrong types for
	 *                             a specific entity or entries that violate
	 *                             Network's integrity contraints
	 */
	public void importFile(String filename) throws ImportFileException {
		try {
            _network.importFile(filename);
        } catch (IOException 
				| UnrecognizedEntryException 
				| BadEntryException 
				| IllegalEntryException e) {
            throw new ImportFileException(filename, e);
    	}
	}

}
