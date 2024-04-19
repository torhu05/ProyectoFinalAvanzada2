package proyectofinal.test;

import static java.time.Duration.ofSeconds;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.vaadin.flow.component.grid.Grid;

import hn.uth.controller.InteractorCliente;
import hn.uth.controller.InteractorImplCliente;
import hn.uth.data.Cliente;
import hn.uth.data.Habitacion;
import hn.uth.data.Reserva;
import hn.uth.views.cliente.ClienteView;
import hn.uth.views.habitacion.HabitacionView;
import hn.uth.views.reserva.ReservaView;

public class ProyectoFinalTest {

	 private List<Cliente> elementos;
	 private ClienteView cliente;
	 
	 private List<Habitacion> elementosH;
	 private HabitacionView habitacion;
	 
	 private List<Reserva> elementosCR;
	 private ReservaView reserva;
	 
	@Test
	public void testGuardarCliente() throws InterruptedException {
		
		cliente = new ClienteView();
        elementos = cliente.RElementos();
		WebDriver driver = new ChromeDriver();
		
		
		
		try {
			// Abre la página web de cliente
			driver.get("http://localhost:8080/Cliente");

			int cantidadClienteInicial = elementos.size();// CONSULTO LA CANTIDAD DE CLIENTE REALES


			new WebDriverWait(driver, ofSeconds(30), ofSeconds(1)).until(titleIs("Cliente"));

			// ESPERA 3 SEGUNDOS DESPUES DE CARGAR LA PANTALLA
			Thread.sleep(3000);

			// Localiza el campo de entrada de nombre de usuario
			WebElement cIdentidad = driver.findElement(By.xpath("//vaadin-text-field[@id='txt_identidad']/input"));
			WebElement cNombre = driver.findElement(By.xpath("//vaadin-text-field[@id='txt_nombre']/input"));
			WebElement cApellido = driver.findElement(By.xpath("//vaadin-text-field[@id='txt_apellido']/input"));
			WebElement cCorreo = driver.findElement(By.xpath("//vaadin-text-field[@id='txt_correo']/input"));
			WebElement cTelefono = driver.findElement(By.xpath("//vaadin-text-field[@id='txt_telefono']/input"));
			WebElement cFechaCumpleaños = driver.findElement(By.xpath("//vaadin-text-field[@id='txt_fechacumpleanos']/input"));
			WebElement cSexo = driver.findElement(By.xpath("//vaadin-combo-box[@id='txt_sexo']/input"));
			WebElement cNacionalidad = driver.findElement(By.xpath("//vaadin-text-field[@id='txt_nacionalidad']/input"));
			WebElement cLugarProcedencia = driver.findElement(By.xpath("//vaadin-text-field[@id='txt_lugarprocedencia']/input"));
			
			
			
			WebElement bGuardar = driver.findElement(By.xpath("//vaadin-button[@id='btn_guardar']"));
			WebElement bCancelar = driver.findElement(By.xpath("//vaadin-button[@id='btn_cancelar']"));
			WebElement bEliminar = driver.findElement(By.xpath("//vaadin-button[@id='btn_eliminar']"));

			// Ingresa el nombre de usuario
			cIdentidad.sendKeys("0801199912345");
			cNombre.sendKeys("Pedro");
			cApellido.sendKeys("Perez");
			cCorreo.sendKeys("grupo1@uth.hn");
			cTelefono.sendKeys("99420000");
			cFechaCumpleaños.sendKeys("23/09/1994");
			cSexo.sendKeys("Masculino");
			cNacionalidad.sendKeys("hondureño");
			cLugarProcedencia.sendKeys("roatan");

			// ESPERA 3 SEGUNDOS LUEGO DE LLENAR LOS CAMPOS PARA LUEGO DAR CLICK EN EL BOTON
			// GUARDAR
			Thread.sleep(3000);

			bGuardar.click();
			Thread.sleep(3000);
			cliente = new ClienteView();
			elementos = cliente.RElementos();
			int cantidadClienteFinal = elementos.size();// CONSULTO LA CANTIDAD DE CLIENTE 
			
			assertTrue(cantidadClienteFinal==cantidadClienteInicial+1);
			// SI LA CANTIDAD DE EMPLEADOS AL DARLE CLICK A GUARDAR AUMENTA EN 1
			Thread.sleep(3000);
		} finally {
			// CIERRA EL NAVEGADOR ABIERTO
			driver.close();
		}
	}
	
	@Test
	public void testEliminarCliente() throws InterruptedException {
		cliente = new ClienteView();
        elementos = cliente.RElementos();
		
		WebDriver driver = new ChromeDriver();
			
		try {
			driver.get("http://localhost:8080/Cliente/0801199912345/edit");

			int cantidadClienteInicial = elementos.size();// CONSULTO LA CANTIDAD DE CLIENTE REALES
			System.out.print(cantidadClienteInicial+"Elimitar");

			new WebDriverWait(driver, ofSeconds(30), ofSeconds(1)).until(titleIs("Cliente"));

			// ESPERA 3 SEGUNDOS DESPUES DE CARGAR LA PANTALLA
			Thread.sleep(3000);

			WebElement bEliminar = driver.findElement(By.xpath("//vaadin-button[@id='btn_eliminar']"));

			
			bEliminar.click();
			Thread.sleep(3000);
			cliente = new ClienteView();
			elementos = cliente.RElementos();
			int cantidadClienteFinal = elementos.size();// CONSULTO LA CANTIDAD DE CLIENTE 
			System.out.print(cantidadClienteFinal+"Elimitar");
			Thread.sleep(3000);
			assertTrue(cantidadClienteFinal==cantidadClienteInicial-1);
			Thread.sleep(3000);
		} finally {
			// CIERRA EL NAVEGADOR ABIERTO
			driver.close();
		}
		
		
		
	}
	
	//test Habitacion
	@Test
	public void testGuardarHabitacion() throws InterruptedException {
		
		habitacion = new HabitacionView();
        elementosH = habitacion.RElementos();
		WebDriver driver = new ChromeDriver();
		
		
		
		try {
			// Abre la página web de cliente
			driver.get("http://localhost:8080/Habitacion");

			int cantidadHabitacionInicial = elementosH.size();// CONSULTO LA CANTIDAD DE CLIENTE REALES
			System.out.print(cantidadHabitacionInicial+"Guartat");

			new WebDriverWait(driver, ofSeconds(30), ofSeconds(1)).until(titleIs("Habitacion"));

			// ESPERA 3 SEGUNDOS DESPUES DE CARGAR LA PANTALLA
			Thread.sleep(3000);

			WebElement cnumerohabitacion = driver.findElement(By.xpath("//vaadin-text-field[@id='txt_numerohabitacion']/input"));
			WebElement cocupacion = driver.findElement(By.xpath("//vaadin-combo-box[@id='cmb_ocupacion']/input"));
			WebElement cprecio = driver.findElement(By.xpath("//vaadin-text-field[@id='txt_precio']/input"));
			WebElement ctipohabitacion = driver.findElement(By.xpath("//vaadin-combo-box[@id='cmb_tipohabitacion']/input"));

			WebElement bsave = driver.findElement(By.xpath("//vaadin-button[@id='btn_guardar']"));

			// Ingresa el nombre de usuario
			cnumerohabitacion.sendKeys("45");
			cocupacion.sendKeys("Cama 3");
			ctipohabitacion.sendKeys("Doble");
			cprecio.sendKeys("4200");

			// ESPERA 3 SEGUNDOS LUEGO DE LLENAR LOS CAMPOS PARA LUEGO DAR CLICK EN EL BOTON
			// GUARDAR
			Thread.sleep(3000);

			bsave.click();
			Thread.sleep(3000);
			habitacion = new HabitacionView();
			elementosH = habitacion.RElementos();
			int cantidadHabitacionFinal = elementosH.size();// CONSULTO LA CANTIDAD DE CLIENTE 
			
			assertTrue(cantidadHabitacionFinal==cantidadHabitacionInicial+1);
			// SI LA CANTIDAD DE EMPLEADOS AL DARLE CLICK A GUARDAR AUMENTA EN 1
			Thread.sleep(3000);
		} finally {
			// CIERRA EL NAVEGADOR ABIERTO
			driver.close();
		}
	}
	
	@Test
	public void testEliminarHabitacion() throws InterruptedException {
		habitacion = new HabitacionView();
        elementosH = habitacion.RElementos();
		
		WebDriver driver = new ChromeDriver();
			
		try {
			driver.get("http://localhost:8080/Habitacion/45/edit");

			int cantidadHabitacionInicial = elementosH.size();// CONSULTO LA CANTIDAD DE CLIENTE REALES

			new WebDriverWait(driver, ofSeconds(30), ofSeconds(1)).until(titleIs("Habitacion"));

			// ESPERA 3 SEGUNDOS DESPUES DE CARGAR LA PANTALLA
			Thread.sleep(3000);

			WebElement bEliminar = driver.findElement(By.xpath("//vaadin-button[@id='btn_eliminar']"));

			
			bEliminar.click();
			Thread.sleep(3000);
			habitacion = new HabitacionView();
			elementosH = habitacion.RElementos();
			int cantidadHabitacionFinal = elementosH.size();// CONSULTO LA CANTIDAD DE CLIENTE 
			Thread.sleep(3000);
			assertTrue(cantidadHabitacionFinal==cantidadHabitacionInicial-1);
			Thread.sleep(3000);
		} finally {
			// CIERRA EL NAVEGADOR ABIERTO
			driver.close();
		}
		
		
		
	}
	
	@Test
	public void testGuardarReserva() throws InterruptedException {
		
		reserva = new ReservaView();
        elementosCR = reserva.RElementos();
		WebDriver driver = new ChromeDriver();
		
		
		
		try {
			// Abre la página web de cliente
			driver.get("http://localhost:8080/CReserva");

			int cantidadReservaInicial = elementosCR.size();// CONSULTO LA CANTIDAD DE CLIENTE REALES
			System.out.print(cantidadReservaInicial+"Resertat");

			new WebDriverWait(driver, ofSeconds(30), ofSeconds(1)).until(titleIs("Crear Reserva"));

			// ESPERA 3 SEGUNDOS DESPUES DE CARGAR LA PANTALLA
			Thread.sleep(3000);

			// Localiza el campo de entrada de nombre de usuario
			WebElement cTicket = driver.findElement(By.xpath("//vaadin-text-field[@id='txt_ticket']/input"));
			WebElement cPrecioTotal = driver.findElement(By.xpath("//vaadin-text-field[@id='txt_precioTotal']/input"));
			WebElement cIdHabitacion = driver.findElement(By.xpath("//vaadin-text-field[@id='txt_idHabitacion']/input"));
			WebElement cIdCliente = driver.findElement(By.xpath("//vaadin-text-field[@id='txt_idCliente']/input"));
			WebElement cFechaInicio = driver.findElement(By.xpath("//vaadin-text-field[@id='txt_fechaInicio']/input"));
			WebElement cFechaFinal = driver.findElement(By.xpath("//vaadin-text-field[@id='txt_fechaFinal']/input"));
					
			
			WebElement bGuardar = driver.findElement(By.xpath("//vaadin-button[@id='btn_guardar']"));
			WebElement bCancelar = driver.findElement(By.xpath("//vaadin-button[@id='btn_Cancelar']"));

			// Ingresa el nombre de usuario
			cTicket.sendKeys("908");
			cPrecioTotal.sendKeys("9000");
			cIdHabitacion.sendKeys("55");
			cIdCliente.sendKeys("2501");
			cFechaInicio.sendKeys("05/10/2024");
			cFechaFinal.sendKeys("23/10/1994");

			// ESPERA 3 SEGUNDOS LUEGO DE LLENAR LOS CAMPOS PARA LUEGO DAR CLICK EN EL BOTON
			// GUARDAR
			Thread.sleep(3000);

			bGuardar.click();
			Thread.sleep(3000);
			reserva = new ReservaView();
			elementosCR = reserva.RElementos();
			int cantidadReservaFinal = elementosCR.size();// CONSULTO LA CANTIDAD DE CLIENTE 

			System.out.print(cantidadReservaFinal+"Resertat");
			assertTrue(cantidadReservaFinal==cantidadReservaInicial+1);
			// SI LA CANTIDAD DE EMPLEADOS AL DARLE CLICK A GUARDAR AUMENTA EN 1
			Thread.sleep(3000);
		} finally {
			// CIERRA EL NAVEGADOR ABIERTO
			driver.close();
		}
	}
	
	@Test
	public void testCancelarReserva() throws InterruptedException {
		
		reserva = new ReservaView();
        elementosCR = reserva.RElementos();
		WebDriver driver = new ChromeDriver();
		
		
		
		try {
			// Abre la página web de cliente
			driver.get("http://localhost:8080/CReserva");

			int cantidadReservaInicial = elementosCR.size();// CONSULTO LA CANTIDAD DE CLIENTE REALES

			new WebDriverWait(driver, ofSeconds(30), ofSeconds(1)).until(titleIs("Crear Reserva"));

			// ESPERA 3 SEGUNDOS DESPUES DE CARGAR LA PANTALLA
			Thread.sleep(3000);

			// Localiza el campo de entrada de nombre de usuario
			WebElement cTicket = driver.findElement(By.xpath("//vaadin-text-field[@id='txt_ticket']/input"));
			WebElement cPrecioTotal = driver.findElement(By.xpath("//vaadin-text-field[@id='txt_precioTotal']/input"));
			WebElement cIdHabitacion = driver.findElement(By.xpath("//vaadin-text-field[@id='txt_idHabitacion']/input"));
			WebElement cIdCliente = driver.findElement(By.xpath("//vaadin-text-field[@id='txt_idCliente']/input"));
			WebElement cFechaInicio = driver.findElement(By.xpath("//vaadin-text-field[@id='txt_fechaInicio']/input"));
			WebElement cFechaFinal = driver.findElement(By.xpath("//vaadin-text-field[@id='txt_fechaFinal']/input"));
					
			
			WebElement bGuardar = driver.findElement(By.xpath("//vaadin-button[@id='btn_guardar']"));
			WebElement bCancelar = driver.findElement(By.xpath("//vaadin-button[@id='btn_Cancelar']"));

			// Ingresa el nombre de usuario
			cTicket.sendKeys("908");
			cPrecioTotal.sendKeys("9000");
			cIdHabitacion.sendKeys("55");
			cIdCliente.sendKeys("2501");
			cFechaInicio.sendKeys("05/10/2024");
			cFechaFinal.sendKeys("23/10/1994");

			// ESPERA 3 SEGUNDOS LUEGO DE LLENAR LOS CAMPOS PARA LUEGO DAR CLICK EN EL BOTON
			// GUARDAR
			Thread.sleep(3000);

			bCancelar.click();
			Thread.sleep(3000);
			reserva = new ReservaView();
			elementosCR = reserva.RElementos();
			int cantidadReservaFinal = elementosCR.size();// CONSULTO LA CANTIDAD DE CLIENTE 

			assertTrue(cTicket.getText().equals(""));
			// SI LA CANTIDAD DE EMPLEADOS AL DARLE CLICK A GUARDAR AUMENTA EN 1
			Thread.sleep(3000);
		} finally {
			// CIERRA EL NAVEGADOR ABIERTO
			driver.close();
		}
	}
	
}
