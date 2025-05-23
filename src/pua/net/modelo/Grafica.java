package pua.net.modelo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import pua.net.dto.GenericaDTO;

import org.jfree.chart.ChartUtilities;
import java.io.*;

public class Grafica {
	
	
	public byte[] getGrafica(String titulo, List<Integer> competencias, List<Integer> materias) throws IOException{
		byte[] imgBytes = null;
		//String nombre2 = "competencias genéricas";
		Iterator<Integer> it;
		Iterator<Integer> itg;
		it = materias.iterator();
		itg = competencias.iterator();
		 
		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
		
		for(int i=0;i<competencias.size();i++){
			
				   dataset.addValue(it.next(), titulo, itg.next());
				 
		 }

		 JFreeChart chart = ChartFactory.createBarChart(
				 titulo, 
		         titulo, "Materias", 
		         dataset,PlotOrientation.VERTICAL, 
		         true, true, false
				 );
		 
		 imgBytes =
			     ChartUtilities.encodeAsPNG(chart.createBufferedImage(560, 367));
		 File BarChart = new File("BarChart.jpeg" ); 
	      ChartUtilities.saveChartAsJPEG( BarChart , chart , 560 , 367 );
	      
		 return imgBytes;	
	}

}

