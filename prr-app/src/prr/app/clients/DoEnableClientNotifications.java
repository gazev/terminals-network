package prr.app.clients;

import prr.Network;
import prr.app.exceptions.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Enable client notifications.
 */
class DoEnableClientNotifications extends Command<Network> {

	DoEnableClientNotifications(Network receiver) {
		super(Label.ENABLE_CLIENT_NOTIFICATIONS, receiver);
		addStringField("key", Prompt.key());
	}

	@Override
	protected final void execute() throws CommandException {
		try {
			if(_receiver.getClientByKey(stringField("key")).notificationsOn()){
				_display.popup(Message.clientNotificationsAlreadyEnabled());
			}
			else{
				_receiver.getClientByKey(stringField("key")).setNotificationsOn(true);
			}
        } catch (prr.exceptions.UnknownClientKeyException e) {
            throw new UnknownClientKeyException(e.getKey());
        }
	}
}
