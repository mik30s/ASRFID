package edu.tarleton.timestamp;

import com.alien.rfid.Bank;
import com.alien.rfid.Mask;
import com.alien.rfid.RFID;
import com.alien.rfid.RFIDReader;
import com.alien.rfid.ReaderException;

import java.sql.Time;

public class TagWriter
{
    public static class TagData {

        private Time time;
        private String uid;

        /**
         * Constructs a TagData Object
         */
        TagData()
        {
            time = new Time(System.currentTimeMillis());
            uid = "ABCD"; // TODO: use johnson trotter to generate random string
        }

        /**
         * Gets the time object
         * @return a Time object
         */
        public Time getTime(){
            return time;
        }

        public String getUID() {
            return uid;
        }
    }

    private String mask;
    private RFIDReader reader;
    private TagData tagData;

    /**
     * Constructs a TagWriter
     * @param mask Mask to use for EPC
     * @throws ReaderException
     */
    TagWriter(String mask) throws ReaderException {
        reader = RFID.open();
        tagData = new TagData();
        this.mask = mask;
    }

    /**
     * Writes the time stamp and uid to the tag.
     * @return true if write was successful
     * @throws ReaderException
     */
    public boolean write() throws ReaderException
    {
        Mask userMask = Mask.maskUser("L33T");
        return reader.write(Bank.EPC, 0, tagData.getTime().toString()).isSuccess()
                && reader.write(Bank.USER, 0, tagData.getUID()).isSuccess();
    }
}
