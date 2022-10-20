package prr.app.terminals;

import prr.Network;
import prr.app.exceptions.UnknownTerminalKeyException;
import prr.terminals.Terminal;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add mode import if needed

/**
 * Open a specific terminal's menu.
 */
class DoOpenMenuTerminalConsole extends Command<Network> {

	DoOpenMenuTerminalConsole(Network receiver) {
		super(Label.OPEN_MENU_TERMINAL, receiver);
        addStringField("key", Prompt.terminalKey());
	}

	@Override
	protected final void execute() throws CommandException {
                try {
                    Terminal t = _receiver.getTerminalByKey(stringField("key"));
                    new prr.app.terminal.Menu(_receiver, t).open();
                } catch (prr.exceptions.UnknownTerminalKeyException e) {
                    throw new UnknownTerminalKeyException(e.getKey());
                }
	}
}
