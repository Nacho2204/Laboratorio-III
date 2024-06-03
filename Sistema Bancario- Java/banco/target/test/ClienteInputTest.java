import java.beans.Transient;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tubanco.presentation.inputs.ClienteInput;


public class ClienteInputTest {
    @Test
    public void testClienteInput() {
        ClienteInput clienteInput = new ClienteInput();
        assertNotNull(clienteInput);
    }

    @Test
    public void testIngresarCliente() {
        ClienteInput clienteInput = new ClienteInput();
        clienteInput.ingresarCliente();
    }
    
}
