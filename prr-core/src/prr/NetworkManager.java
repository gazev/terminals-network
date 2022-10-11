package prr;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import prr.exceptions.ImportFileException;
import prr.exceptions.MissingFileAssociationException;
import prr.exceptions.UnavailableFileException;
import prr.exceptions.UnrecognizedEntryException;

//FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Manage access to network and implement load/save operations.
 */
public class NetworkManager {

	/** The network itself. */
	private Network _network = new Network();

	/** The name of the file associated with current Network instance */
	private String _filename = "";

	/** Signals if Network is dirty (has unsaved changes) */
	private boolean _dirtyFlag = false;

	/**
	 * 
	 * @return the current network instance
	 */
    public Network getNetwork() {
		return _network;
	}

	/**
	 * 
	 * @return name of the file associated with the current Network instance
	 */
	public String getFilename() {
		return _filename;
	}

	/**
	 * 
	 * @return returns true if current Network instance is dirty
	 */
	public boolean isDirty() {
		return _dirtyFlag;
	}

	/**
	 * 
	 * @param dirtyFlag signals if current Network is dirty 
	 */
	public void setDirtyFlag(boolean dirtyFlag) {
		_dirtyFlag = dirtyFlag;
	}

	public void setFilename(String filename) {
		_filename = filename;
	}


	/**
	 * @param filename name of the file containing the serialized application's state
         *        to load.
	 * @throws UnavailableFileException if the specified file does not exist or there is
         *         an error while processing this file.
	 */
	public void load(String filename) throws UnavailableFileException {
		try {
			FileInputStream f = new FileInputStream(filename);
			ObjectInputStream o = new ObjectInputStream(f);
			// load saved network object into _network 
			_network = (Network) o.readObject();
			o.close();
		} catch(IOException | ClassNotFoundException e) {
			throw new UnavailableFileException(filename);
		}
		setFilename(filename);
	}

	/**
         * Saves the serialized application's state into the file associated to the current network.
         *
	 * @throws FileNotFoundException if for some reason the file cannot be created or opened. 
	 * @throws MissingFileAssociationException if the current network does not have a file.
	 * @throws IOException if there is some error while serializing the state of the network to disk.
	 */
	public void save() throws FileNotFoundException, MissingFileAssociationException, IOException {
		/* If there are no changes, return */
		if(!isDirty()) {
			return;
		}

		/* No file associated with this instance of the Network */
		if(getFilename().isBlank() || getFilename() == null)
			throw new MissingFileAssociationException();

		/* Write Network object to file */
		try {
			FileOutputStream f = new FileOutputStream(getFilename());
			BufferedOutputStream b = new BufferedOutputStream(f);
			ObjectOutput o = new ObjectOutputStream(b);
			o.writeObject(_network);
			o.close();
		} catch(IOException e) {
			throw new IOException();
		}
		
		/* After save, Network data is not dirty */
		setDirtyFlag(false);
	}

	/**
         * Saves the serialized application's state into the specified file. The current network is
         * associated to this file.
         *
	 * @param filename the name of the file.
	 * @throws FileNotFoundException if for some reason the file cannot be created or opened.
	 * @throws MissingFileAssociationException if the current network does not have a file.
	 * @throws IOException if there is some error while serializing the state of the network to disk.
	 */
	public void saveAs(String filename) throws FileNotFoundException, MissingFileAssociationException, IOException {
		setFilename(filename);
		save();
	}

	/**
	 * Read text input file and create domain entities..
	 * 
	 * @param filename name of the text input file
	 * @throws ImportFileException
	 */
	public void importFile(String filename) throws ImportFileException {
		try {
                        _network.importFile(filename);
                } catch (IOException | UnrecognizedEntryException /* FIXME maybe other exceptions */ e) {
                        throw new ImportFileException(filename, e);
    }
	}

}
