package yk;

import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
/**
 * ͻ����ʾ
 *
 */
@WebServlet("/PieChart2")
public class PieChart2 extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DefaultPieDataset data = new DefaultPieDataset();
		
		data.setValue("ƻ��",100); 
		data.setValue("����",200); 
		data.setValue("����",300); 
		data.setValue("�㽶",400); 
		data.setValue("��֦",500); 
        
		JFreeChart chart = ChartFactory.createPieChart(
        		"ˮ������ͼ",  		// ͼ�����
		        data, 
		        true, 			// �Ƿ���ʾͼ��
		        false, 			// �Ƿ񴴽�������ʾ (tooltip) 
                false  			// �Ƿ����� URL ����
		        ); 
        
		PiePlot pieplot = (PiePlot)chart.getPlot();
        pieplot.setExplodePercent("�㽶", 0.5); 

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
        
		request.getRequestDispatcher("index2.jsp").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
