package abc ;

import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;

public class FileSplit
{
    private int nMax = 1400;
    private final String extension = ".XYZ";

    public boolean split(File file)
    {
        try
        {
            String strPath = file.getPath();
            String strFilename = file.getName();
            String name = strFilename.substring(0, strFilename.length()-4) + "000";
            String strDir = "c:\\"+strFilename+"/";
            byte[] input = new byte[1024];
            File pDirectory = new File(strDir);
            pDirectory.mkdir();
            FileInputStream pRead = new FileInputStream(strPath);
            FileOutputStream pWrite = new FileOutputStream(strDir+name+extension);
            FileWriter pMerge = new FileWriter(strDir+name.substring(0, name.length()-3) +".bat");
            pMerge.write("copy /b "+name+extension);
            int nLen, nCtr=0;
            while((nLen = pRead.read(input))>0)
            {
                if(nCtr < nMax)
                    nCtr++;
                else
                {
                    nCtr=0;
                    pWrite.close();
                    name = nextFilename(name);
                    pMerge.write(" + "+name+extension);
                    pWrite = new FileOutputStream(strDir+name+extension);
                }
                pWrite.write(input, 0, nLen);
            }
            pMerge.write(" \""+strFilename+"\"");
            pMerge.close();
            pRead.close();
            pWrite.close();
            return true;
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
            return false;
        }
    }
    public String nextFilename(String oldName)
    {
        String newName="", temp;
        int i, nNum;
        for(i=0; i<oldName.length()-3; i++)
            newName+=oldName.charAt(i);

        temp = oldName.substring(i);
        nNum = 1+Integer.parseInt(temp);

        if(nNum < 10)
            newName += "00"+nNum;
        else if(nNum < 100)
            newName += "0"+nNum;
        else
            newName += nNum;

        return newName;
    }
    public int getMax()
    {
        return nMax;
    }
    public void setMax(int n)
    {
        nMax = n;
    }
}
