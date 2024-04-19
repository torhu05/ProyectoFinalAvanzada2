package hn.uth.views.cliente;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.vaadin.flow.theme.lumo.LumoIcon;

import hn.uth.controller.InteractorCliente;
import hn.uth.controller.InteractorImplCliente;
import hn.uth.data.Reserva;
import hn.uth.data.Cliente;
import hn.uth.views.MainLayout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.swing.JOptionPane;

import org.springframework.data.domain.PageRequest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

@PageTitle("Cliente")
@Route(value = "Cliente/:samplePersonID?/:action?(edit)", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@Uses(Icon.class)
public class ClienteView extends Div implements BeforeEnterObserver, ViewModelCliente {

    private final String SAMPLEPERSON_ID = "samplePersonID";
    private final String SAMPLEPERSON_EDIT_ROUTE_TEMPLATE = "Cliente/%s/edit";

    public final Grid<Cliente> grid = new Grid<>(Cliente.class, false);
    
    

    private TextField identidad;
    private TextField nombre; 
    private TextField apellido;
    private TextField correo;
    private TextField telefono;
    private TextField fechacumpleanos;
    private ComboBox<String> sexo;
    private TextField nacionalidad;
    private TextField lugarProcedencia;

    private final Button cancel = new Button("Cancelar");
    private final Button save = new Button("Guardar", new Icon(VaadinIcon.CHECK_CIRCLE));
    private final Button eliminar = new Button("Eliminar", new Icon(VaadinIcon.CLOSE_CIRCLE));
    
    public Cliente ClienteSeleccionado;
    private List<Cliente> elementos;
    private InteractorCliente controlador;

    private Cliente samplePerson;



    public ClienteView() {
        addClassNames("cliente-view");
        
        controlador = new InteractorImplCliente(this);
        elementos = new ArrayList<>();

        
        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("identidad").setAutoWidth(true).setHeader("Identidad");
        grid.addColumn("nombre").setAutoWidth(true).setHeader("Nombre");
        grid.addColumn("apellido").setAutoWidth(true).setHeader("Apellido");
        grid.addColumn("correo").setAutoWidth(true).setHeader("Correo");
        grid.addColumn("telefono").setAutoWidth(true).setHeader("Telefono");
        grid.addColumn("fechacumpleanos").setAutoWidth(true).setHeader("Fecha de Cumpleaños");
        grid.addColumn("sexo").setAutoWidth(true).setHeader("Sexo");
        grid.addColumn("nacionalidad").setAutoWidth(true).setHeader("Nacionalidad");
        grid.addColumn("lugarprocedencia").setAutoWidth(true).setHeader("Lugar de Procedencia");
        
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(SAMPLEPERSON_EDIT_ROUTE_TEMPLATE, event.getValue().getIdentidad()));
            } else {
                clearForm();
                UI.getCurrent().navigate(ClienteView.class);
            }
        });
        
        controlador.consultarCliente();


        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.ClienteSeleccionado == null) {
                    this.ClienteSeleccionado = new Cliente();
                    this.ClienteSeleccionado.setIdentidad(identidad.getValue());
                    this.ClienteSeleccionado.setNombre(nombre.getValue());
                    this.ClienteSeleccionado.setApellido(apellido.getValue());
                    this.ClienteSeleccionado.setCorreo(correo.getValue());
                    this.ClienteSeleccionado.setTelefono(telefono.getValue());
                    this.ClienteSeleccionado.setFechacumpleanos(fechacumpleanos.getValue().toString());
                    this.ClienteSeleccionado.setSexo(sexo.getValue());
                    this.ClienteSeleccionado.setNacionalidad(nacionalidad.getValue());
                    this.ClienteSeleccionado.setLugarprocedencia(lugarProcedencia.getValue());
                    
                    this.controlador.CrearCliente(ClienteSeleccionado);
                }else {
                	this.ClienteSeleccionado.setIdentidad(identidad.getValue());
                    this.ClienteSeleccionado.setNombre(nombre.getValue());
                    this.ClienteSeleccionado.setApellido(apellido.getValue());
                    this.ClienteSeleccionado.setCorreo(correo.getValue());
                    this.ClienteSeleccionado.setTelefono(telefono.getValue());
                    this.ClienteSeleccionado.setFechacumpleanos(fechacumpleanos.getValue().toString());
                    this.ClienteSeleccionado.setSexo(sexo.getValue());
                    this.ClienteSeleccionado.setNacionalidad(nacionalidad.getValue());
                    this.ClienteSeleccionado.setLugarprocedencia(lugarProcedencia.getValue());
                    
                    this.controlador.ActualizarCliente(ClienteSeleccionado);
                }

                clearForm();
                refreshGrid();
                //Notification.show("Data updated");
                UI.getCurrent().navigate(ClienteView.class);
            } catch (ObjectOptimisticLockingFailureException exception) {
                Notification n = Notification.show(
                        "Error updating the data. Somebody else has updated the record while you were making changes.");
                n.setPosition(Position.MIDDLE);
                n.addThemeVariants(NotificationVariant.LUMO_ERROR);

           
            

            } 
        });
        
        eliminar.addClickListener(e -> {
        	if(this.ClienteSeleccionado == null) {
        		mostrarMensajeError("seleccione un cliente para poder eliminar");
        	}else {
        		this.controlador.EliminarCliente(ClienteSeleccionado.getIdentidad());
        		clearForm();
                refreshGrid();
                UI.getCurrent().navigate(ClienteView.class);
        	}
        	
        	
        });
       
    } 
    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<String> ClienteId = event.getRouteParameters().get(SAMPLEPERSON_ID);
        if (ClienteId.isPresent()) {
        	Cliente ClienteObtenido = obtenerCliente(ClienteId.get());
            if (ClienteObtenido!=null) {
                populateForm(ClienteObtenido);
            } else {
                Notification.show(
                        String.format("Cliente con identidad %s no existe", ClienteId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(ClienteView.class);

            }
        }
       

    }

    private Cliente obtenerCliente(String identidad) {
		// TODO Auto-generated method stub
    	
    	Cliente encontrado = null;
    	for(Cliente res: elementos) {
    		if(res.getIdentidad().equals(identidad)) {
    			encontrado = res;
    			break;
    		}
    	}
		return encontrado;
	}
    
    
	private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        identidad = new TextField("identidad");
        identidad.setPrefixComponent(VaadinIcon.USER_CHECK.create()); 
        identidad.setId("txt_identidad");
        nombre = new TextField("nombre");
        nombre.setPrefixComponent(VaadinIcon.USER_CARD.create());
        nombre.setId("txt_nombre");
        apellido = new TextField("Apellido");
        apellido.setPrefixComponent(VaadinIcon.USER_CARD.create());
        apellido.setId("txt_apellido");
        correo = new TextField("Correo");
        correo.setPrefixComponent(VaadinIcon.AT.create());
        correo.setId("txt_correo");
        telefono = new TextField("Telefono");
        telefono.setPrefixComponent(VaadinIcon.PHONE.create());
        telefono.setId("txt_telefono");
        fechacumpleanos = new TextField("Fecha Cumpleanos");
        fechacumpleanos.setPrefixComponent(VaadinIcon.DATE_INPUT.create());
        fechacumpleanos.setId("txt_fechacumpleanos");
        sexo = new ComboBox<>("Sexo");
        sexo.setPrefixComponent(VaadinIcon.FAMILY.create());
        sexo.setAllowCustomValue(true);
        sexo.setItems("Femenino","Masculino");
        sexo.setId("txt_sexo");
        
        nacionalidad = new TextField("Nacionalidad");
        nacionalidad.setPrefixComponent(VaadinIcon.MAP_MARKER.create());
        nacionalidad.setId("txt_nacionalidad");
        lugarProcedencia = new TextField("Lugar de Procedencia");
        lugarProcedencia.setPrefixComponent(VaadinIcon.AIRPLANE.create());
        lugarProcedencia.setId("txt_lugarprocedencia");
        formLayout.add(identidad, nombre, apellido, correo, telefono, fechacumpleanos, sexo, nacionalidad, lugarProcedencia);

        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        cancel.setId("btn_cancelar");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.setId("btn_guardar");
        eliminar.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
        eliminar.setId("btn_eliminar");        
        buttonLayout.add(save, eliminar, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setClassName("grid-wrapper");
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
        grid.setId("gr_tabla");
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
        this.controlador.consultarCliente();
    }

    private void clearForm() {
        populateForm(null);
    }

    public void populateForm(Cliente value) {

        this.ClienteSeleccionado = value;
       if(value != null) {
    	   identidad.setValue(value.getIdentidad());
    	   nombre.setValue(value.getNombre());
    	   apellido.setValue(value.getApellido());
    	   correo.setValue(value.getCorreo());
    	   telefono.setValue(value.getTelefono());
    	   fechacumpleanos.setValue(value.getFechacumpleanos());
    	   sexo.setValue(value.getSexo());
    	   nacionalidad.setValue(value.getNacionalidad());
    	   lugarProcedencia.setValue(value.getLugarprocedencia());   
       }else {
    	   identidad.setValue("");
    	   nombre.setValue("");
    	   apellido.setValue("");
    	   correo.setValue("");
    	   telefono.setValue("");
    	   fechacumpleanos.setValue("");
    	   sexo.setValue("");
    	   nacionalidad.setValue("");
    	   lugarProcedencia.setValue(""); 
       }


    }
	@Override
	public void mostrarClientesEnGrid(List<Cliente> items) {
		Collection<Cliente> itemsCollection = items;
		grid.setItems(itemsCollection);
		this.elementos = items;
	}
	@Override
	public void mostrarMensajeError(String mensaje) {
		Notification n = Notification.show(mensaje);
        n.setPosition(Position.MIDDLE);
        n.addThemeVariants(NotificationVariant.LUMO_ERROR);
		
	}
	@Override
	public void mostrarMensajeExito(String mensaje) {
		Notification notification = new Notification();
		notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
		
		Icon icon = VaadinIcon.CHECK_CIRCLE.create();
		
		var layout = new HorizontalLayout(icon, new Text(mensaje));
		layout.setAlignSelf(FlexComponent.Alignment.CENTER);
		
		notification.add(layout);
		
		notification.setPosition(Position.MIDDLE);		
		notification.setDuration(2000);
		notification.open();	
		
	}
	
	@Override
	public List<Cliente> RElementos() {
		return elementos;	
		
	}
	
}
