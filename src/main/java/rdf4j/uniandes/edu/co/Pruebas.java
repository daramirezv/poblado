package rdf4j.uniandes.edu.co;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import java.util.HashMap;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;

public class Pruebas {
	
	//OBJECT PROPERTIES
	static final String BASE = "http://www.grupo7.semanticweb.uniandes.edu.co/curso/articles/";
	static final String HASTOPIC = BASE + "hasTopic";
	static final String LOCATEDIN = BASE + "locatedIn";
	static final String PRESENTEDIN = BASE + "presentedIn";
	static final String PUBLISHEDBY = BASE + "publishedBy";
	static final String PUBLISHEDINCITY = BASE + "publishedInCity";
	static final String PUBLISHEDINCOUNTRY = BASE + "publishedInCountry";
	static final String WRITTENBY = BASE + "writtenBy";
	
	//DATA PROPERTIES
	static final String CITYNAME = BASE + "cityName";
	static final String CONFERENCENAME = BASE + "conferenceName";
	static final String COUNTRYNAME = BASE + "countryName";
	static final String FULLNAME = BASE + "fullName";
	static final String NUMBER = BASE + "number";
	static final String PAGE = BASE + "page";
	static final String PUBLISHERNAME = BASE + "publisherName";
	static final String TITLE = BASE + "title";
	static final String URL = BASE + "url";
	static final String VOLUME = BASE + "volume";
	static final String YEAR = BASE + "year";

	//CLASES
	static final String ARTICLE = BASE + "article" + "/";
	static final String AUTHOR = BASE + "author" + "/";
	static final String CITY = BASE + "city" + "/";
	static final String CONFERENCE = BASE + "conference" + "/";
	static final String COUNTRY = BASE + "country" + "/";
	static final String PUBLISHER = BASE + "publisher" + "/";
	static final String INSTITUTION = BASE + "institution" + "/";
	static final String JOURNAL = BASE + "journal" + "/";
	static final String TOPIC = BASE + "topic" + "/";

	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		int contadorJDOG = 0;
		int contadorDAOG = 0;
		int contadorJMOG = 0;
		int contadorAFOG = 0;
		int contadorJD = 0;
		int contadorDA = 0;
		int contadorJM = 0;
		int contadorAF = 0;

		//hashmaps de todas las clases
		HashMap<String, String> hashArticulos = new HashMap<>();
		HashMap<String, String> hashAutores = new HashMap<>();
		HashMap<String, String> hashCiudades = new HashMap<>();
		HashMap<String, String> hashPaises = new HashMap<>();
		HashMap<String, String> hashPublishers = new HashMap<>();
		HashMap<String, String> hashConferencia = new HashMap<>();
		HashMap<String, String> hashInstituciones = new HashMap<>();
		HashMap<String, String> hashJournals = new HashMap<>();
		HashMap<String, String> hashTopics = new HashMap<>();

		InputStream inputstream = new FileInputStream("C:\\Users\\usuario\\Downloads\\final2.owl");
		Model model = Rio.parse(inputstream, "", RDFFormat.RDFXML);
		ValueFactory vf = SimpleValueFactory.getInstance();

		//ANDRES ARCHIVO 1
		try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\usuario\\Downloads\\CSV_1_Andres.csv"))) {
			String line;
			//No se lee la primera linea
			line = br.readLine();
			
			while ((line = br.readLine()) != null) {
				String[] values = line.split("\",\"");
				contadorAFOG++;
				//si no tiene cuatro campos la linea, salta a la siguiente linea
				if(values.length != 4)
					continue;
				
				//si el articulo no tiene una longitud valida, salta a la siguiente linea
				if(values[0].length() < 3)
					continue;
				
				contadorAF++;
				
				for(int pos = 0; pos < 4; pos++)
				{
					String nombreArti = "";
				
					//TITULO ARTICULO
					if(pos==0)
					{
						int tamanoArticulo = values[pos].length();
						String articuloArreglado = values[pos].substring(1,tamanoArticulo);
						
						if(tamanoArticulo < 3)
							continue;
						
						//SI NO EXISTE EL ARTICULO EN EL HASH
						if(hashArticulos.get(articuloArreglado) == null)
						{
							hashArticulos.put(articuloArreglado, articuloArreglado);
							IRI articuloTemp = vf.createIRI(ARTICLE, articuloArreglado);
							model.add(articuloTemp, RDFS.CLASS, vf.createIRI(ARTICLE, ""));
							model.add(articuloTemp, vf.createIRI(TITLE, ""), vf.createLiteral(articuloArreglado));
							nombreArti = articuloArreglado;
						}
						
						//SI EXISTE EN EL HASH
						else
						{
							nombreArti = articuloArreglado;
						}
						//System.out.println(values[pos].substring(1,tamano));
					}
					
					//TOPICOS
					else if(pos==3)
					{
						int tamano = values[pos].length();
						
						if(tamano < 3)
							continue;
							//System.out.println("NULO");
						else
						{
							String[] topicosTemp = values[pos].substring(0,tamano-1).split(",");
							
							for(int m = 0; m < topicosTemp.length; m++)
							{
								if(topicosTemp[m].length() < 3)
									continue;
								
								if(m != 0)
								{
									int tamanoTop = topicosTemp[m].length();
									String topicoCorregido = topicosTemp[m].substring(1, tamanoTop);
									IRI topicTemp = vf.createIRI(TOPIC, topicoCorregido);

									if(hashTopics.get(topicoCorregido) == null)
									{
										hashTopics.put(topicoCorregido, topicoCorregido);
										model.add(topicTemp, RDFS.CLASS, vf.createIRI(TOPIC, ""));
									}
									
									IRI articuloTemp = vf.createIRI(ARTICLE, nombreArti);
									model.add(articuloTemp, vf.createIRI(HASTOPIC, ""), topicTemp);
								}
								else if(m != topicosTemp.length-1)
								{
									IRI topicTemp = vf.createIRI(TOPIC, topicosTemp[m]);

									if(hashTopics.get(topicosTemp[m]) == null)
									{
										hashTopics.put(topicosTemp[m], topicosTemp[m]);
										model.add(topicTemp, RDFS.CLASS, vf.createIRI(TOPIC, ""));
									}
									
									IRI articuloTemp = vf.createIRI(ARTICLE, nombreArti);
									model.add(articuloTemp, vf.createIRI(HASTOPIC, ""), topicTemp);
								}
								else
								{
									int tamanoTop = topicosTemp[m].length();
									String topicoCorregido = topicosTemp[m].substring(1, tamanoTop-1);
									IRI topicTemp = vf.createIRI(TOPIC, topicoCorregido);

									if(hashTopics.get(topicoCorregido) == null)
									{
										hashTopics.put(topicoCorregido, topicoCorregido);
										model.add(topicTemp, RDFS.CLASS, vf.createIRI(TOPIC, ""));
									}
									
									IRI articuloTemp = vf.createIRI(ARTICLE, nombreArti);
									model.add(articuloTemp, vf.createIRI(HASTOPIC, ""), topicTemp);
								}
							}
						}
							//System.out.println(values[pos].substring(0,tamano-1));
					}
					
					//ANIO
					else if(pos==1)
					{
						int tamano = values[pos].length();
						
						if(tamano < 3)
							continue;
						
						IRI nombreArtiTemp = vf.createIRI(ARTICLE, nombreArti);
						model.add(nombreArtiTemp, vf.createIRI(YEAR, ""), vf.createLiteral(values[pos]));
						//System.out.println(values[pos]);
					}
					
					//PUBLICADORES
					else
					{
						IRI nombreArtiTemp = vf.createIRI(ARTICLE, nombreArti);
						IRI institutionTemp = vf.createIRI(INSTITUTION, values[pos]);

						if(hashInstituciones.get(values[pos])==null)
						{
							hashInstituciones.put(values[pos], values[pos]);
							model.add(institutionTemp, RDFS.CLASS, vf.createIRI(INSTITUTION, ""));
							model.add(institutionTemp, vf.createIRI(PUBLISHERNAME, ""), vf.createLiteral(values[pos]));
						}
						
						model.add(nombreArtiTemp, vf.createIRI(PUBLISHEDBY, ""), institutionTemp);
					}
				}
			}
		}
		
		//ANDRES ARCHIVO 2
		try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\usuario\\Downloads\\CSV_2_Andres.csv"))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		        String[] values = line.split("\",\"");
		        
		        if(values.length != 2)
	        		continue;
		        
		        if(values[1].length() < 3)
					continue;
		        
		        for(int pos = 0; pos < 2; pos++)
		        {
		        	int tamano = values[pos].length();
		        	
		        	if(tamano < 3)
		        		continue;
		        	
		        	else if(pos==0)
		        	{
		        		//System.out.println(values[pos].substring(1,tamano));
		        		String autoArreglados = values[pos].substring(1,tamano);
		        		int tamanoArticulo = values[pos+1].length();
		        		
		        		if(hashAutores.get(autoArreglados)==null)
		        		{
		        			hashAutores.put(autoArreglados, autoArreglados);
							IRI autorTemp = vf.createIRI(AUTHOR, autoArreglados);
							IRI articleTemp = vf.createIRI(ARTICLE, values[pos+1].substring(0,tamanoArticulo-1));
							model.add(autorTemp, RDFS.CLASS, vf.createIRI(AUTHOR, ""));
							model.add(autorTemp, vf.createIRI(FULLNAME, ""), vf.createLiteral(autoArreglados));
							model.add(articleTemp, vf.createIRI(WRITTENBY, ""), autorTemp);

		        		}
		        	}
	        			//System.out.println(values[pos].substring(0,tamano-1));
		        }
		    }
		}
		
		//JUAN MANUEL ARCHIVO 1
		try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\usuario\\Downloads\\CSV_1_JM.csv"))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		        String[] values = line.split("\",\"");
		        contadorJMOG++;
		        if(values.length != 2)
	        		continue;
		        
		        if(values[0].length() < 3)
	        		continue;
		        
		        contadorJM++;
		        
		        for(int pos = 1; pos > -1; pos--)
		        {
		        	String nombreArticuloTemp = "";
		        	int tamano = values[pos].length();
		        	
		        	if(tamano < 3)
		        		break;
		        	
		        	//nombre articulo
		        	else if(pos==0)
		        	{
		        		String autorArreglado = values[pos].substring(1,tamano);
		        		if(hashAutores.get(autorArreglado) == null)
				        {
		        			hashAutores.put(autorArreglado, autorArreglado);
							IRI articleTemp = vf.createIRI(ARTICLE, nombreArticuloTemp);
							IRI autorTemp = vf.createIRI(AUTHOR, autorArreglado);
							model.add(autorTemp, RDFS.CLASS, vf.createIRI(AUTHOR, ""));
							model.add(articleTemp, vf.createIRI(WRITTENBY), autorTemp);
							model.add(autorTemp, vf.createIRI(FULLNAME, ""), vf.createLiteral(autorArreglado));
				        }
		        		else
		        		{
		        			IRI articleTemp = vf.createIRI(ARTICLE, nombreArticuloTemp);
							IRI autorTemp = vf.createIRI(AUTHOR, autorArreglado);
							model.add(articleTemp, vf.createIRI(WRITTENBY), autorTemp);
		        		}
		        		
		        		//System.out.println(values[pos].substring(1,tamano));
		        	}
		        	else
		        	{
		        		String nombreArreglado = values[pos].substring(0,tamano-1);
		        		if(hashArticulos.get(nombreArreglado) == null)
				        {
				        	hashArticulos.put(nombreArreglado, nombreArreglado);
							IRI articleTemp = vf.createIRI(ARTICLE, nombreArreglado);
							model.add(articleTemp, RDFS.CLASS, vf.createIRI(ARTICLE));
							model.add(articleTemp, vf.createIRI(TITLE, ""), vf.createLiteral(values[pos]));
							nombreArticuloTemp = nombreArreglado;
				        }
		        		else
		        		{
							nombreArticuloTemp = nombreArreglado;
		        		}
		        	}
	        			//System.out.println(values[pos].substring(0,tamano-1));
		        }
		    }
		}
		
		//JUAN MANUEL ARCHIVO 2
		try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\usuario\\Downloads\\CSV_2_JM.csv"))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] values = line.split("\",\"");

				if(values.length != 3)
					continue;

				if(values[0].length() < 3)
	        		continue;
		        
				for(int pos = 0; pos < 3; pos++)
				{
					int tamano = values[pos].length();

					if(tamano < 3)
						continue;
					
					else if(pos==0)
					{
						//System.out.println(values[pos].substring(1,tamano));
					}
					else if(pos==2)
					{
						int tamanoArti = values[0].length();
						IRI nombreArtiTemp = vf.createIRI(ARTICLE, values[0].substring(1,tamanoArti));
						model.add(nombreArtiTemp, vf.createIRI(YEAR, ""), vf.createLiteral(values[pos].substring(0,tamano-1)));
						//System.out.println(values[pos]);
						//System.out.println(values[pos].substring(0,tamano-1));
					}
					else
					{
						int tamanoArti = values[0].length();
						
						if(hashPublishers.get(values[pos]) == null)
				        {
							hashPublishers.put(values[pos], values[pos]);
							IRI articleTemp = vf.createIRI(ARTICLE, values[0].substring(1,tamanoArti));
							IRI publiTemp = vf.createIRI(PUBLISHER, values[pos]);
							model.add(publiTemp, RDFS.CLASS, vf.createIRI(PUBLISHER));
							model.add(publiTemp, vf.createIRI(PUBLISHERNAME, ""), vf.createLiteral(values[pos]));
							model.add(articleTemp, vf.createIRI(PUBLISHEDBY, ""), publiTemp);
				        }
						else
						{
							IRI articleTemp = vf.createIRI(ARTICLE, values[0].substring(1,tamanoArti));
							IRI publiTemp = vf.createIRI(PUBLISHER, values[pos]);
							model.add(articleTemp, vf.createIRI(PUBLISHEDBY, ""), publiTemp);
						}
					}
						//System.out.println(values[pos]);
				}
			}
		}
		
		//DAVID RAMIREZ
		try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\usuario\\Downloads\\CSV_1_David.csv"))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] values = line.split("\\|");
				contadorDAOG++;
				if(values.length != 6)
					continue;
				
				if(values[4].length() < 3)
	        		continue;
				
				contadorDA++;
				
				for(int pos = 0; pos < 6; pos++)
				{
					int tamano = values[pos].length();

					if(tamano < 3)
						continue;
					
					if(pos == 0)
					{
						if(hashConferencia.get(values[pos]) == null)
						{
							hashConferencia.put(values[pos], values[pos]);
							IRI conferenciaTemp = vf.createIRI(CONFERENCE, values[pos]);
							IRI articleTemp = vf.createIRI(ARTICLE, values[4]);
							model.add(conferenciaTemp, RDFS.CLASS, vf.createIRI(CONFERENCE, ""));
							model.add(conferenciaTemp, vf.createIRI(CONFERENCENAME, ""), vf.createLiteral(values[pos]));
							model.add(articleTemp, vf.createIRI(PRESENTEDIN, ""), conferenciaTemp);

						}
						else
						{
							IRI conferenciaTemp = vf.createIRI(CONFERENCE, values[pos]);
							IRI articleTemp = vf.createIRI(ARTICLE, values[4]);
							model.add(articleTemp, vf.createIRI(PRESENTEDIN, ""), conferenciaTemp);
						}

					}
					else if(pos == 1)
					{
						if(hashCiudades.get(values[pos]) == null)
						{
							hashCiudades.put(values[pos], values[pos]);
							IRI ciudadTemp = vf.createIRI(CITY, values[pos]);
							IRI articleTemp = vf.createIRI(ARTICLE, values[4]);
							model.add(ciudadTemp, RDFS.CLASS, vf.createIRI(CITY, ""));
							model.add(ciudadTemp, vf.createIRI(CITYNAME, ""), vf.createLiteral(values[pos]));
							model.add(articleTemp, vf.createIRI(PUBLISHEDINCITY, ""), ciudadTemp);

						}
						else
						{
							IRI ciudadTemp = vf.createIRI(CITY, values[pos]);
							IRI articleTemp = vf.createIRI(ARTICLE, values[4]);
							model.add(articleTemp, vf.createIRI(PUBLISHEDINCITY, ""), ciudadTemp);
						}
					}
					else if(pos == 2)
					{
						if(hashPaises.get(values[pos]) == null)
						{
							hashPaises.put(values[pos], values[pos]);
							IRI paisTemp = vf.createIRI(COUNTRY, values[pos]);
							IRI articleTemp = vf.createIRI(ARTICLE, values[4]);
							model.add(paisTemp, RDFS.CLASS, vf.createIRI(COUNTRY, ""));
							model.add(paisTemp, vf.createIRI(COUNTRYNAME, ""), vf.createLiteral(values[pos]));
							model.add(articleTemp, vf.createIRI(PUBLISHEDINCOUNTRY, ""), paisTemp);

						}
						else
						{
							IRI paisTemp = vf.createIRI(COUNTRY, values[pos]);
							IRI articleTemp = vf.createIRI(ARTICLE, values[4]);
							model.add(articleTemp, vf.createIRI(PUBLISHEDINCOUNTRY, ""), paisTemp);
						}
					}
					else if(pos == 3)
					{
						IRI articleTemp = vf.createIRI(ARTICLE, values[4]);
						model.add(articleTemp, vf.createIRI(YEAR, ""), vf.createLiteral(values[pos]));
					}
					else if(pos == 4)
					{
						if(hashArticulos.get(values[pos]) == null)
						{
							hashArticulos.put(values[pos], values[pos]);
							IRI articleTemp = vf.createIRI(ARTICLE, values[pos]);
							model.add(articleTemp, RDFS.CLASS, vf.createIRI(ARTICLE, ""));
							model.add(articleTemp, vf.createIRI(TITLE, ""), vf.createLiteral(values[pos]));
						}
					}
					else
					{
						String[] listaAutores = values[pos].split(",");
						for (String stringAuto : listaAutores) {
							if(hashAutores.get(stringAuto) == null)
							{
								hashAutores.put(stringAuto, stringAuto);
								IRI autorTemp = vf.createIRI(AUTHOR, values[pos]);
								IRI articleTemp = vf.createIRI(ARTICLE, values[4]);
								model.add(autorTemp, RDFS.CLASS, vf.createIRI(AUTHOR, ""));
								model.add(autorTemp, vf.createIRI(FULLNAME, ""), vf.createLiteral(values[pos]));
								model.add(articleTemp, vf.createIRI(WRITTENBY, ""), autorTemp);	
							}
							else
							{
								IRI autorTemp = vf.createIRI(AUTHOR, values[pos]);
								IRI articleTemp = vf.createIRI(ARTICLE, values[4]);
								model.add(articleTemp, vf.createIRI(WRITTENBY, ""), autorTemp);	
							}
						}
					}
					//System.out.println(values[pos]);
				}
			}
		}
		
		//JUAN DA
		try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\usuario\\Downloads\\CSV_1_JD.csv"))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",\"");
				contadorJDOG++;
				if(values[0].length() < 3)
				{
					continue;
				}
				
				contadorJD++;
				
				if(values.length == 3)
				{
					for(int pos = 0; pos < 3; pos++)
					{
						int tamano = values[pos].length();

						if(tamano < 3)
							continue;
						
						else if(pos==0)
						{
							String tituloArreglado = values[pos].substring(1,tamano);
							if(hashArticulos.get(tituloArreglado) == null)
							{
								hashArticulos.put(tituloArreglado, tituloArreglado);
								IRI articleTemp = vf.createIRI(ARTICLE, tituloArreglado);
								model.add(articleTemp, RDFS.CLASS, vf.createIRI(ARTICLE, ""));
								model.add(articleTemp, vf.createIRI(TITLE, ""), vf.createLiteral(tituloArreglado));
							}
							//System.out.println(values[pos].substring(1,tamano));
						}
						else if(pos==1)
						{
							int tamanoArticulo = values[0].length();
							String[] listaAutores = values[pos].substring(1,tamano).split(",");
							for (String stringAuto : listaAutores) {
								if(hashAutores.get(stringAuto) == null)
								{
									hashAutores.put(stringAuto, stringAuto);
									IRI autorTemp = vf.createIRI(AUTHOR, stringAuto);
									IRI articleTemp = vf.createIRI(ARTICLE, values[0].substring(1,tamanoArticulo));
									model.add(autorTemp, RDFS.CLASS, vf.createIRI(AUTHOR, ""));
									model.add(autorTemp, vf.createIRI(FULLNAME, ""), vf.createLiteral(stringAuto));
									model.add(articleTemp, vf.createIRI(WRITTENBY, ""), autorTemp);	
								}
								else
								{
									IRI autorTemp = vf.createIRI(AUTHOR, stringAuto);
									IRI articleTemp = vf.createIRI(ARTICLE, values[0].substring(1,tamanoArticulo));
									model.add(articleTemp, vf.createIRI(WRITTENBY, ""), autorTemp);	
								}
							}
							//System.out.println(values[pos].substring(1,tamano));
						}
						else
						{
							int tamanoArticulo = values[0].length();
							String[] temp = values[pos].split(",");
							for(int g = 1; g<7; g++)
							{
								if(g==1)
								{
									IRI articleTemp = vf.createIRI(ARTICLE, values[0].substring(1,tamanoArticulo));
									model.add(articleTemp, vf.createIRI(YEAR, ""), vf.createLiteral(temp[g]));
								}
								else if(g==2)
								{
									IRI articleTemp = vf.createIRI(ARTICLE, values[0].substring(1,tamanoArticulo));
									model.add(articleTemp, vf.createIRI(URL, ""), vf.createLiteral(temp[g]));
								}
								else if(g==3)
								{
									if(hashJournals.get(temp[g]) == null)
									{
										hashJournals.put(temp[g], temp[g]);
										IRI pubTemp = vf.createIRI(JOURNAL, temp[g]);
										IRI articleTemp = vf.createIRI(ARTICLE, values[0].substring(1,tamanoArticulo));
										model.add(pubTemp, RDFS.CLASS, vf.createIRI(JOURNAL, ""));
										model.add(pubTemp, vf.createIRI(PUBLISHERNAME, ""), vf.createLiteral(temp[g]));
										model.add(articleTemp, vf.createIRI(PUBLISHEDBY, ""), pubTemp);	
									}
									else
									{
										IRI pubTemp = vf.createIRI(PUBLISHER, temp[g]);
										IRI articleTemp = vf.createIRI(ARTICLE, values[0].substring(1,tamanoArticulo));
										model.add(articleTemp, vf.createIRI(PUBLISHEDBY, ""), pubTemp);
									}
								}
								else if(g==4)
								{
									IRI articleTemp = vf.createIRI(ARTICLE, values[0].substring(1,tamanoArticulo));
									model.add(articleTemp, vf.createIRI(VOLUME, ""), vf.createLiteral(temp[g]));
								}
								else if(g==5)
								{
									IRI articleTemp = vf.createIRI(ARTICLE, values[0].substring(1,tamanoArticulo));
									model.add(articleTemp, vf.createIRI(NUMBER, ""), vf.createLiteral(temp[g]));
								}
								else
								{
									int tamanoG = temp[g].length();
									IRI articleTemp = vf.createIRI(ARTICLE, values[0].substring(1,tamanoArticulo));
									model.add(articleTemp, vf.createIRI(PAGE, ""), vf.createLiteral(temp[g].substring(0,tamanoG-2)));
									//System.out.println(temp[g].substring(0,tamanoG-2));
								}
									//System.out.println(temp[g]);
							}
						}
					}
				}
				else if(values.length == 4)
				{
					for(int pos = 0; pos < 4; pos++)
					{
						int tamano = values[pos].length();

						if(tamano < 3)
							continue;
						
						if(pos==0)
						{
							String tituloArreglado = values[pos].substring(1,tamano);
							if(hashArticulos.get(tituloArreglado) == null)
							{
								hashArticulos.put(tituloArreglado, tituloArreglado);
								IRI articleTemp = vf.createIRI(ARTICLE, tituloArreglado);
								model.add(articleTemp, RDFS.CLASS, vf.createIRI(ARTICLE, ""));
								model.add(articleTemp, vf.createIRI(TITLE, ""), vf.createLiteral(tituloArreglado));
							}
							//System.out.println(values[pos].substring(1,tamano));
						}
						else if(pos==1)
						{
							int tamanoArticulo = values[0].length();
							String[] listaAutores = values[pos].substring(1,tamano).split(",");
							for (String stringAuto : listaAutores) {
								if(hashAutores.get(stringAuto) == null)
								{
									hashAutores.put(stringAuto, stringAuto);
									IRI autorTemp = vf.createIRI(AUTHOR, stringAuto);
									IRI articleTemp = vf.createIRI(ARTICLE, values[0].substring(1,tamanoArticulo));
									model.add(autorTemp, RDFS.CLASS, vf.createIRI(AUTHOR, ""));
									model.add(autorTemp, vf.createIRI(FULLNAME, ""), vf.createLiteral(stringAuto));
									model.add(articleTemp, vf.createIRI(WRITTENBY, ""), autorTemp);	
								}
								else
								{
									values[0].substring(1,tamanoArticulo);
									IRI autorTemp = vf.createIRI(AUTHOR, stringAuto);
									IRI articleTemp = vf.createIRI(ARTICLE, values[0].substring(1,tamanoArticulo));
									model.add(articleTemp, vf.createIRI(WRITTENBY, ""), autorTemp);	
								}
							}
							//System.out.println(values[pos].substring(1,tamano));
						}
						else if(pos==2)
						{
							int tamanoArticulo = values[0].length();
							String[] temp = values[pos].split(",");
							for(int g = 1; g<3; g++)
							{
								if(g==1)
								{
									IRI articleTemp = vf.createIRI(ARTICLE, values[0].substring(1,tamanoArticulo));
									model.add(articleTemp, vf.createIRI(YEAR, ""), vf.createLiteral(temp[g]));
								}
								else if(g==2)
								{
									IRI articleTemp = vf.createIRI(ARTICLE, values[0].substring(1,tamanoArticulo));
									model.add(articleTemp, vf.createIRI(URL, ""), vf.createLiteral(temp[g]));
								}
								//System.out.println(temp[g]);
							}
						}
						else if(pos == 3)
						{
							int tamanoArticulo = values[0].length();
							String[] temp = values[pos].split("\"\",");
							for(int g = 0; g<2; g++)
							{
								if(g==0)
								{
									int tamanoG = temp[g].length();
									String publiArreglaro = temp[g].substring(1,tamanoG);
									if(hashJournals.get(temp[g]) == null)
									{
										hashJournals.put(publiArreglaro, publiArreglaro);
										IRI pubTemp = vf.createIRI(JOURNAL, publiArreglaro);
										IRI articleTemp = vf.createIRI(ARTICLE, values[0].substring(1,tamanoArticulo));
										model.add(pubTemp, RDFS.CLASS, vf.createIRI(JOURNAL, ""));
										model.add(pubTemp, vf.createIRI(PUBLISHERNAME, ""), vf.createLiteral(publiArreglaro));
										model.add(articleTemp, vf.createIRI(PUBLISHEDBY, ""), pubTemp);	
									}
									else
									{
										IRI pubTemp = vf.createIRI(JOURNAL, temp[g]);
										IRI articleTemp = vf.createIRI(ARTICLE, values[0].substring(1,tamanoArticulo));
										model.add(articleTemp, vf.createIRI(PUBLISHEDBY, ""), pubTemp);
									}
									//System.out.println(temp[g].substring(1,tamanoG));
								}
								else
								{
									String[] internoFinal = temp[g].split(",");
									for(int z = 0; z < 3;z++)
									{
										if(z==0)
										{
											IRI articleTemp = vf.createIRI(ARTICLE, values[0].substring(1,tamanoArticulo));
											model.add(articleTemp, vf.createIRI(VOLUME, ""), vf.createLiteral(internoFinal[z]));
										}
										else if(z==1)
										{
											IRI articleTemp = vf.createIRI(ARTICLE, values[0].substring(1,tamanoArticulo));
											model.add(articleTemp, vf.createIRI(NUMBER, ""), vf.createLiteral(internoFinal[z]));
										}
										else if(z==2)
										{
											int tamanoZ = internoFinal[z].length();
											IRI articleTemp = vf.createIRI(ARTICLE, values[0].substring(1,tamanoArticulo));
											model.add(articleTemp, vf.createIRI(PAGE, ""), vf.createLiteral(internoFinal[z].substring(0,tamanoZ-2)));
											//System.out.println(internoFinal[z].substring(0,tamanoZ-2));
										}
									}
								}
							}
						}
					}
				}
			}
		}
		System.out.println("AF:" + contadorAF + " AFOG:"+ contadorAFOG);
		System.out.println("JM:" + contadorJM + " JMOG:"+ contadorJMOG);
		System.out.println("DA:" + contadorDA + " DAOG:"+ contadorDAOG);
		System.out.println("JD:" + contadorJD + " AFOG:"+ contadorJDOG);

		FileWriter fw = new FileWriter("C:\\Users\\usuario\\Downloads\\respuesta.owl");
		Rio.write(model, System.out, RDFFormat.RDFXML);
	}
}
