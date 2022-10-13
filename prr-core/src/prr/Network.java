package prr;

import java.io.Serializable;
import java.io.IOException;
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

	public void importRegisterClient(String id, String nome, int taxId) {
	}



	public void registerFromFields(String[] _fields) throws UnrecognizedEntryException{
	}


	/**
	 * Read text input file and create corresponding domain entities.
	 *
	 * @param filename name of the text input file
         * @throws UnrecognizedEntryException if some entry is not correct
	 * @throws IOException if there is an IO erro while processing the text file
	 */
	void importFile(String filename) throws UnrecognizedEntryException, IOException  {
    }
}

