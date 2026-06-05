package com.plazoleta.usuarios;

import com.plazoleta.usuarios.UserServicesApplication;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled("Requiere base de datos MySQL corriendo")
@SpringBootTest(classes = UserServicesApplication.class)
class UserServicesApplicationTests {

	@Test
	void contextLoads() {
	}
}
