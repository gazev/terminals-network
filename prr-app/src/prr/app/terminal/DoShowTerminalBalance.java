package prr.app.terminal;

import prr.Network;
import prr.app.exceptions.UnknownTerminalKeyException;
import prr.terminals.Terminal;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Show balance.
 */
class DoShowTerminalBalance extends TerminalCommand {

	DoShowTerminalBalance(Network context, Terminal terminal) {
		super(Label.SHOW_BALANCE, context, terminal);
	}

	@Override
	protected final void execute() throws CommandException {
		_display.popup(
			Message.terminalPaymentsAndDebts(_receiver.getKey(), 
												(int) Math.round(_receiver.getPaidBalance()), 
													(int) Math.round(_receiver.getPaidBalance())));
	}
}
