package edu.tarleton.timestamp;

import com.alien.rfid.RFID;
import com.alien.rfid.RFIDReader;
import com.alien.rfid.RFIDResult;
import com.alien.rfid.ReaderException;
import com.alien.rfid.Tag;

import java.util.ArrayList;

public class TagReader
{
    public RFIDReader reader;

    /**
     * Constructs a TagReader for reading RFID tags
     * @throws ReaderException
     */
    public TagReader() throws ReaderException {
        reader = RFID.open();
    }

    /**
     * Reads a single tag from the RFID device and returns it.
     * @return an RFID tag
     */
    public Tag read() throws ReaderException
    {
        RFIDResult result = reader.read();
        if (!result.isSuccess())
        {
            throw new ReaderException("Failed to read tag");
        }
        return (Tag)result.getData();
    }

    public ArrayList<Tag> readContinously() {
        return null;
    }
}
