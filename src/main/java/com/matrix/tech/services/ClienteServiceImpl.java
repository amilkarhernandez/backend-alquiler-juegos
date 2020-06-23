package com.matrix.tech.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

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
import org.springframework.validation.BindingResult;

import com.matrix.tech.dao.IClienteDao;
import com.matrix.tech.models.Cliente;

@Service
public class ClienteServiceImpl implements IClienteService{

	@Autowired
	private IClienteDao clienteDao;
	
	@Override
	public List<Cliente> finAll() {
		return (List<Cliente>)clienteDao.findAll();
	}

	@Override
	public Cliente findById(Long id) {
		return clienteDao.findById(id).orElse(null);
	}

	@Override
	public Cliente save(Cliente cliente) {
		SimpleDateFormat formato = new SimpleDateFormat("yyyy", Locale.US);
		Date fechaActual = new Date();
		Long edad = Long.valueOf(formato.format(fechaActual)) - Long.valueOf(formato.format(cliente.getFecha_nac()));
		cliente.setEdad(edad);
		return clienteDao.save(cliente);
	}

	@Override
	public void delete(Long id) {
		clienteDao.deleteById(id);
	}

	@Override
	public List<String> validacion(BindingResult result) {
		return result.getFieldErrors().stream()
				.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
				.collect(Collectors.toList());
	}

	@Override
	public Cliente Actualizar(Cliente clienteActual, Cliente Actualizado) {
		clienteActual.setNit(Actualizado.getNit());
		clienteActual.setNombres(Actualizado.getNombres());
		clienteActual.setApellidos(Actualizado.getApellidos());
		clienteActual.setTelefono(Actualizado.getTelefono());
		clienteActual.setGenero(Actualizado.getGenero());
		clienteActual.setFecha_nac(Actualizado.getFecha_nac());
		
		return clienteActual;
	}

	@Override
	public Cliente findByNit(String nit) {
		return clienteDao.findByNit(nit);
	}

	@Override
	public Long count() {
		return clienteDao.count();
	}

	@Override
	public List<Cliente> findTopCliente() {
		return clienteDao.findTopCliente();
	}
	
	public ByteArrayInputStream exportClientes(List<Cliente> cliente) throws IOException {
		String[] columns = {"ID","NIT", "NOMBRES", "APELLIDOS", "TELEFONO", "GENERO", "FECHA NACIMIENTO", "EDAD"};
		
		try(Workbook workBook = new XSSFWorkbook();
				ByteArrayOutputStream out = new ByteArrayOutputStream();){
			CreationHelper creationHelper = workBook.getCreationHelper();
			
			Sheet sheet = workBook.createSheet("Clientes");
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
			
			for (Cliente cli: cliente) {
				Row row = sheet.createRow(rowIndex++);
				row.createCell(0).setCellValue(cli.getId());
				row.createCell(1).setCellValue(cli.getNit());
				row.createCell(2).setCellValue(cli.getNombres());
				row.createCell(3).setCellValue(cli.getApellidos());
				row.createCell(4).setCellValue(cli.getTelefono());
				row.createCell(5).setCellValue(cli.getGenero());
				row.createCell(6).setCellValue(cli.getFecha_nac());
				row.createCell(7).setCellValue(cli.getEdad());
			}
			workBook.write(out);
			
			return new ByteArrayInputStream(out.toByteArray());
		}
				
				
	}

}
