package prr.app.terminal;

import prr.Network;
import prr.terminals.Terminal;
import prr.app.exceptions.UnknownTerminalKeyException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Command for sending a text communication.
 */
class DoSendTextCommunication extends TerminalCommand {

        DoSendTextCommunication(Network context, Terminal terminal) {
                super(Label.SEND_TEXT_COMMUNICATION, context, terminal, receiver -> receiver.canStartCommunication());
                addStringField("originKey", Prompt.terminalKey());
                addStringField("text", Prompt.textMessage());
        }

        @Override
        protected final void execute() throws CommandException {
                try {
                        _receiver.sendTextCommunication(
                                        stringField("originKey"),
                                                stringField("text"), _network);
                } catch (prr.exceptions.UnavailableTerminalException e1) {
                        _display.popup(Message.destinationIsOff(e1.getKey()));
                } catch (prr.exceptions.UnknownTerminalKeyException e2) {
                        throw new UnknownTerminalKeyException(e2.getKey());
                }
        }
} 
