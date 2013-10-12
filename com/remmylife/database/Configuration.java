package com.remmylife.database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
/** *//**
* ��ȡproperties�ļ�
* @author Qutr
*
*/
public class Configuration
{
    private Properties propertie;
    private FileInputStream inputFile;
    private FileOutputStream outputFile;
    /** *//**
     * ��ʼ��Configuration��
     */
    public Configuration()
    {
        propertie = new Properties();
    }
    /** *//**
     * ��ʼ��Configuration��
     * @param filePath Ҫ��ȡ�������ļ���·��+����
     */
    public Configuration(String filePath)
    {
        propertie = new Properties();
        try {
            inputFile = new FileInputStream(filePath);
            propertie.load(inputFile);
            inputFile.close();
        } catch (FileNotFoundException ex) {
            System.out.println("��ȡ�����ļ�--->ʧ�ܣ�- ԭ���ļ�·����������ļ�������");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("װ���ļ�--->ʧ��!");
            ex.printStackTrace();
        }
    }//end ReadConfigInfo()
    /** *//**
     * ���غ������õ�key��ֵ
     * @param key ȡ����ֵ�ļ�
     * @return key��ֵ
     */
    public String getValue(String key)
    {
        if(propertie.containsKey(key)){
            String value = propertie.getProperty(key);//�õ�ĳһ���Ե�ֵ
            return value;
        }
        else
            return "";
    }//end getValue()
    /** *//**
     * ���غ������õ�key��ֵ
     * @param fileName properties�ļ���·��+�ļ���
     * @param key ȡ����ֵ�ļ�
     * @return key��ֵ
     */
    public String getValue(String fileName, String key)
    {
        try {
            String value = "";
            inputFile = new FileInputStream(fileName);
            propertie.load(inputFile);
            inputFile.close();
            if(propertie.containsKey(key)){
                value = propertie.getProperty(key);
                return value;
            }else
                return value;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }//end getValue()
    /** *//**
     * ���properties�ļ������е�key����ֵ
     */
    public void clear()
    {
        propertie.clear();
    }//end clear();
    /** *//**
     * �ı�����һ��key��ֵ����key������properties�ļ���ʱ��key��ֵ��value�����棬
     * ��key������ʱ����key��ֵ��value
     * @param key Ҫ����ļ�
     * @param value Ҫ�����ֵ
     */
    public void setValue(String key, String value)
    {
        propertie.setProperty(key, value);
    }//end setValue()
    /** *//**
     * �����ĺ���ļ����ݴ���ָ�����ļ��У����ļ��������Ȳ����ڡ�
     * @param fileName �ļ�·��+�ļ�����
     * @param description �Ը��ļ�������
     */
    public void saveFile(String fileName, String description)
    {
        try {
            outputFile = new FileOutputStream(fileName);
            propertie.store(outputFile, description);
            outputFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }//end saveFile()

}