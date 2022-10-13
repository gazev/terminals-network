package prr.app.main;

import java.io.FileNotFoundException;
import java.io.IOError;
import java.io.IOException;
import java.util.MissingFormatArgumentException;

import prr.NetworkManager;
import prr.app.exceptions.FileOpenFailedException;
import prr.exceptions.MissingFileAssociationException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
//FIXME add more imports if needed

/**
 * Command to save a file.
 */
class DoSaveFile extends Command<NetworkManager> {

	DoSaveFile(NetworkManager receiver) {
		super(Label.SAVE_FILE, receiver);
	}

	@Override
	protected final void execute() {
		// tries to save, if there is no file perform saveAs action
		try {
			try {
				_receiver.save();
			} catch (MissingFileAssociationException e) {
				saveAs();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
}

	public void saveAs() throws IOException {
		// prompts user for filename and save, if empty filename retry 
		try {
			_receiver.saveAs(Form.requestString(Prompt.newSaveAs()));
		} catch (MissingFileAssociationException e) {
			saveAs();
		}
	}
}
