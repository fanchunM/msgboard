package com.mine.product.msgboard.ui.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import org.springframework.web.multipart.MultipartFile;

public class BASE64DecodedMultipartFile implements MultipartFile
{
    private final byte[] imgContent;
    private final String header;
  
    public BASE64DecodedMultipartFile(byte[] imgContent, String header) {
        this.imgContent = imgContent;
        this.header = header.split(";")[0];
    }
    @Override
    public String getName()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getOriginalFilename()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getContentType()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isEmpty()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public long getSize()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public byte[] getBytes()
        throws IOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public InputStream getInputStream()
        throws IOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void transferTo(File dest)
        throws IOException, IllegalStateException
    {
        // TODO Auto-generated method stub
        
    }
    public static MultipartFile base64ToMultipart(String base64) {
        String[] baseStrs = base64.split(",");
  
        byte[] b = new byte[0];
        b= Base64.getDecoder().decode(baseStrs[1]);
  
        for (int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {
                b[i] += 256;
            }
        }
        return new BASE64DecodedMultipartFile(b, baseStrs[0]);
    }
}
