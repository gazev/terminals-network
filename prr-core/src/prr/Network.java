package prr;

import java.io.Serializable;
import java.io.IOException;
import prr.exceptions.UnrecognizedEntryException;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Class Store implements a store.
 */
public class Network implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	/**
	 * ___________________________________________________________________Mapas e Listas__________________________________________________________________________________________
	*/
	private Map<String, Client> __clients = new TreeMap<String, Client>(String.CASE_INSENSITIVE_ORDER);


        // FIXME define attributes
        // FIXME define contructor(s)
        // FIXME define methods















  	// ---------------------------- _____metodos imports______ ------------------------------------------------  //


	public void importRegisterClient(String id, String nome, int taxId) {
		Client _client = new Client(id, nome, taxId);
		_clients.put(id, _client);
	}





	public void registerFromFields(String[] _fields) throws UnrecognizedEntryException{
		if (_fields[0].equals("CLIENT")){
			int _taxID = Integer.parseInt(_fields[3]);
			importRegisterClient(_fields[1], _fields[2], _taxID);
		} else if(_fields[0].equals("BASIC")){


		} else if(_fields[0].equals("FANCY")){

		} else if(_fields[0].equals("FRIENDS")){

		} else {
			throw new UnrecognizedEntryException(_fields[0]); // Cria uma nova excecao que e apanhada no importFile
		}

	}


	/**
	 * Read text input file and create corresponding domain entities.
	 *
	 * @param filename name of the text input file
         * @throws UnrecognizedEntryException if some entry is not correct
	 * @throws IOException if there is an IO erro while processing the text file
	 */
	void importFile(String filename) throws UnrecognizedEntryException, IOException  {
		BufferedReader reader = new BufferedReader(new FileReader(txtfile));
		String linhaa;
		while ((linhaa = reader.readLine()) != null) {
			String[] fields = linha.split("\\|");
			try {
				registerFromFields(fields);
			} catch (BadEntryException bee) {
				System.err.printf("WARNING: data desconhecida %s\n", bee.getMessage());
				bee.printStackTrace();
			}
		}
		reader.close();
        }
}

