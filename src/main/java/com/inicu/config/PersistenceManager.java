package com.inicu.config;

import java.io.File;
import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.Document;
import org.springframework.core.io.Resource;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.inicu.postgres.utility.BasicConstants;
/** 
 * Enum :-To create EntityManager to communicate with postgres.
 * @author iNICU 
 *
 */
public enum PersistenceManager {
  INSTANCE;
  private EntityManagerFactory emFactory;
  //private EntityManagerFactory emFactoryKalawati;
  
  private PersistenceManager() {
      emFactory = Persistence.createEntityManagerFactory("inicudb");
  }
  public EntityManager getEntityManager() {
	  return emFactory.createEntityManager();
  }
  
  public void close() {
    emFactory.close();
  }
}