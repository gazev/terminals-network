package prr.communications;

import prr.terminals.Terminal;

public abstract class Communication implements Comparable<Communication> {
    /** Static counter of total Communications on the Network */
    private static Integer _count = 0;

    /** Communication identifying integer */
    protected final Integer _number;

    /** Terminal that started this Communication */
    protected Terminal _sender;

    /** Terminal that received this Communication */
    protected Terminal _receiver;

    /** Marks Communication as finished or on going */
    protected boolean _finished;

    /** Marks Communication has paid */
    protected boolean _paid;

    /** Text chars or duration units */
    protected Integer _units;

    protected Double _price;

    /**
     * 
     * @param sender Terminal starting the communication
     * @param receiver Terminal receiving the communication
     */
    Communication(Terminal sender, Terminal receiver) {
       _number = Communication._count++;
       _sender = sender;
       _receiver = receiver; 
       _paid = false;
       _price = 0.0;
    }

    // TODO
    public Integer getNumber() { return _number; }

    public Terminal getSender() { return _sender; }

    public Terminal getReciever() { return _receiver; }

    public Double getPrice() { return _price; }

    public boolean isFinished() { return _finished; }

    public boolean isPaid() { return _paid; }

    public Integer getUnits() { return _units; }

    public void setFinished() { _finished = true; }

    public void setPaid() { _paid = true; }

    public void setUnits(Integer units) { _units = units; }

    public void setPrice(Double price) { _price = price; } 

    // public abstract Integer determinePrice(TariffPlan tp);

    @Override
    public int compareTo(Communication c) {
        return _number - c.getNumber();
    }

    // TODO - calculatePrice...
    public String toString() {
        return "TEXT|" +
            _number + "|" +
            _sender.getKey() + "|" +
            _receiver.getKey() + "|" +
            _units + "|" +
            (int) Math.round(_price) + "|" +
            (_finished ? "FINISHED" : "ONGOING");
    }
    
}
