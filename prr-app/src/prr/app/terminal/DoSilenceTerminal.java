package prr.app.terminal;

import prr.Network;
import prr.terminals.SilentTerminalState;
import prr.terminals.Terminal;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Silence the terminal.
 */
class DoSilenceTerminal extends TerminalCommand {

	DoSilenceTerminal(Network context, Terminal terminal) {
		super(Label.MUTE_TERMINAL, context, terminal);
	}

	@Override
	protected final void execute() throws CommandException {
		try {
			_receiver.changeTerminalState(new SilentTerminalState());
		} catch (prr.exceptions.SameTerminalStateException e) {
			_display.popup(Message.alreadySilent());
		}
	}
}
