package bot.necessities.comfirmcommands;

public class ConfirmCommandType {

    String[] input;
    Long msgid;
    ConfirmCommand command;
    Long chid;

    public ConfirmCommandType(String[] input, Long msgid, ConfirmCommand command, Long chid) {

        this.input = input;
        this.msgid = msgid;
        this.command = command;
        this.chid = chid;

    }

    public String[] getInput() {
        return input;

    }
    public Long getMsgid() {
        return msgid;
    }

    public ConfirmCommand getCommand() {
        return command;
    }

    public Long getChid() {
        return chid;
    }
}