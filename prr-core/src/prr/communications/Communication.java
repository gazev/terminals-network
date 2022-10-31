package prr.communications;

import prr.clients.TariffTable;
import prr.terminals.Terminal;

/** Communication in the Network */
public abstract class Communication implements Comparable<Communication> {
    /** Static counter of total Communications on the Network */
    private static Integer _count = 1;

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
    }
 
    /**
     * 
     * @return Identifying number of this communication
     */
    public Integer getNumber() { return _number; }

    /**
     * 
     * @return Terminal sender of this Communication
     */
    public Terminal getSender() { return _sender; }

    /**
     * 
     * @return Terminal receiver of this Communication
     */
    public Terminal getReciever() { return _receiver; }

    /**
     * 
     * @return Price of this communication
     */
    public Double getPrice() { return _price; }

    /**
     * 
     * @return True if this Communication is finished
     */
    public boolean isFinished() { return _finished; }

    /**
     * 
     * @return True if this Communication is paid
     */
    public boolean isPaid() { return _paid; }

    /**
     * 
     * @return Units of this Communication
     */
    public Integer getUnits() { return _units; }


    /**
     * Marks communication as paid
     */
    public void setPaid() { _paid = true; }

    /**
     * Set the units of the communication
     * 
     * @param units Units of this communication
     */
    public void setUnits(Integer units) { _units = units; }

    /**
     * Set price of the communication
     * 
     * @param price Price of the Communication
     */
    public void setPrice(Double price) { _price = price; } 

    /**
     * Sets the price of this communication based on a TariffTable
     * 
     * @param tp The Tariff Table used to calculate the price
     */
    public abstract void determinePrice(TariffTable tp);

    /**
     * Natural order of Communications is by ascending order of their Numbers
     * 
     * @see java.lang.Comparable#compareTo(Object) 
     */ 
    @Override
    public int compareTo(Communication c) {
        return _number - c.getNumber();
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
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
