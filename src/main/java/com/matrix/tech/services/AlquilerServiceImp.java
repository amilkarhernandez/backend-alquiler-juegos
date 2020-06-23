package com.matrix.tech.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matrix.tech.dao.IAlquilerDao;
import com.matrix.tech.dao.IVideojuegoDao;
import com.matrix.tech.models.Alquiler;
import com.matrix.tech.models.ItemAlquiler;
import com.matrix.tech.models.VideoJuego;

@Service
public class AlquilerServiceImp implements IAlquilerService{
	

	@Autowired
	private IAlquilerDao alquilerDao;
	
	@Autowired
	private IVideojuegoDao videojuegoDao;
	
	@Override
	public Alquiler findAlquilerById(Long id) {
		return alquilerDao.findById(id).orElse(null);
	}

	@Override
	public Alquiler saveAlquiler(Alquiler alquiler) {
		return alquilerDao.save(alquiler);
	}

	@Override
	public void deleteAlquilerById(Long id) {
		alquilerDao.deleteById(id);
	}

	@Override
	public List<VideoJuego> findByTitulo(String term) {
		return videojuegoDao.findByTitulo(term);
	}

	@Override
	public int restarStock(int stock, int cantidad) {
		return stock-cantidad;
	}

	@Override
	public List<Alquiler> findByNit(String nit) {
		return alquilerDao.findByNit(nit);
	}


	@Override
	public List<Alquiler> findByFechaVenta(String fecha) {
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		Date miFecha = null;
		try {
			miFecha = formato.parse(fecha);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return alquilerDao.findByFecha(miFecha);
		
	}

	@Override
	public List<Alquiler> findAllByEstado() {
		return (List<Alquiler>) alquilerDao.findAllByEstado();
	}

	@Override
	public Long count() {
		return alquilerDao.count();
	}

	@Override
	public ByteArrayInputStream exportAlquiler(List<Alquiler> alquiler) throws IOException {
		String[] columns = {"ID","NIT", "NOMBRES", "APELLIDOS", "VIDEO JUEGO", "FECHA ALQUILADO", "FECHA ENTREGA", "TOTAL"};
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		
		try(Workbook workBook = new XSSFWorkbook();
				ByteArrayOutputStream out = new ByteArrayOutputStream();){
			CreationHelper creationHelper = workBook.getCreationHelper();
			
			Sheet sheet = workBook.createSheet("Alquileres");
			sheet.autoSizeColumn(columns.length);
			
			Font headerFont = workBook.createFont();
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.BLACK.getIndex());
			
			CellStyle cellStyle = workBook.createCellStyle();
			cellStyle.setFont(headerFont);
			
			Row headerRow = sheet.createRow(0);
			
			//Header
			for (int col = 0; col < columns.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(columns[col]);
				cell.setCellStyle(cellStyle);
			}
			
			CellStyle cellStyle1 = workBook.createCellStyle();
			cellStyle1.setDataFormat(creationHelper.createDataFormat().getFormat("#"));
			
			int rowIndex=1;
			
			for (Alquiler alq: alquiler) {
				Row row = sheet.createRow(rowIndex++);
				row.createCell(0).setCellValue(alq.getId());
				row.createCell(1).setCellValue(alq.getCliente().getNit());
				row.createCell(2).setCellValue(alq.getCliente().getNombres());
				row.createCell(3).setCellValue(alq.getCliente().getApellidos());
				for(ItemAlquiler items: alq.getItems()) {
					row.createCell(4).setCellValue(items.getVideojuego().getTitulo());
					row.createCell(7).setCellValue(items.getCantidad()*items.getVideojuego().getValorUnitario());
				}
				row.createCell(5).setCellValue(alq.getFecha());
				row.createCell(6).setCellValue(alq.getFecha_entrega());
				
			}
			workBook.write(out);
			
			return new ByteArrayInputStream(out.toByteArray());
		}
	}

	




}
