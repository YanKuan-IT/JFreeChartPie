package yk;

import java.awt.Font;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;
/**
 * ���ɱ�״ͼ����Ӹ����⣬��Ӱٷֱȣ����԰ٶȣ������ҵ�
 * 
 */
@WebServlet("/PieChart")
public class PieChart extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		DefaultPieDataset data = new DefaultPieDataset();
		
		data.setValue("ƻ��",100); 
		data.setValue("����",200); 
		data.setValue("����",300); 
		data.setValue("�㽶",400); 
		data.setValue("��֦",500); 
        
        JFreeChart chart = ChartFactory.createPieChart3D(
//   	JFreeChart chart = ChartFactory.createPieChart(
        		"ˮ������ͼ",  		// ͼ�����
		        data, 
		        true, 			// �Ƿ���ʾͼ��
		        false, 			// �Ƿ񴴽�������ʾ (tooltip) 
                false  			// �Ƿ����� URL ����
		        ); 
        
        // ������
		chart.addSubtitle(new TextTitle("2013���"));
		//��ʾ�ٷֱ�
		PiePlot pieplot = (PiePlot)chart.getPlot();
        pieplot.setLabelFont(new Font("����", 0, 12));
        pieplot.setNoDataMessage("������");
        pieplot.setCircular(true);
        pieplot.setLabelGap(0.02D);
        pieplot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} {2}",NumberFormat.getNumberInstance(),new DecimalFormat("0.00%")));
//        pieplot.setExplodePercent("�㽶", 0.5); //ChartFactory.createPieChartʱ������3D����ʾЧ��
        
        PiePlot3D pieplot3d = (PiePlot3D)chart.getPlot(); 
		//���ÿ�ʼ�Ƕ�  
		pieplot3d.setStartAngle(120D);  
		//���÷���Ϊ��˳ʱ�뷽��  
		pieplot3d.setDirection(Rotation.CLOCKWISE);  
		//����͸���ȣ�0.5FΪ��͸����1Ϊ��͸����0Ϊȫ͸��  
		pieplot3d.setForegroundAlpha(0.7F); 
        
        FileOutputStream fos_jpg = null; 
        long currentTimeMillis = System.currentTimeMillis();
        String filename = currentTimeMillis+".jpeg";
        try { 
        	//��ͼƬ������Tomcat������WebRootĿ¼��
            fos_jpg = new FileOutputStream(request.getSession().getServletContext().getRealPath("/")+filename);
            ChartUtilities.writeChartAsJPEG(fos_jpg,1,chart,600,500,null); 
        } catch (Exception e) {
        	System.out.println("error");
		} finally { 
            try { 
                fos_jpg.close(); 
            } catch (Exception e) {
            	System.out.println("error2");
            } 
        }
        request.setAttribute("filename", filename);
        
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
