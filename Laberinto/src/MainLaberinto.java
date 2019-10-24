import java.util.Scanner;

public class MainLaberinto {
	
	static Scanner scanner = new Scanner(System.in);
	
	static int iter = 0; 
	static int countStar = 0;
	
	static int size;
	static int row;
	static int column;
	
	static String matrix[][];
	
public static void main(String args[]) {	
	
	System.out.println("Introduzca el tamaño deseado para la matriz:");	
	size = scanner.nextInt();
	scanner.nextLine();
		
	if(size > 0) {
		matrix = new String[size][size];
		ask4Values();
		scanner.close();
	}	
//Error 1: Tamaño menor que 1	
	else {
		System.err.println("Error: Tamaño del laberinto invalido.");
	}
}

/*
 * Metodo que usa el scanner para preguntar y asignar los valores de cada posicion de la matriz
*/ 
public static void ask4Values() {
	
	if(column == 0 && row < size) {

		 String inputRow = scanner.nextLine();
		
		 String[] parts = inputRow.split("\\s+");
		 
		 if(parts.length != size) {
//Error 2: Numero de valores por fila incorrectos			 
			 System.err.println("Error: tamaño de la entrada invalido");
		 }
		 else {
		 setRow(parts, row);
		 iter = 0;
		 column++;
		 }
		ask4Values();
	}
	else if(row <  size) {
		//Aumentamos la posicion de la fila en uno y reseteamos la de las columnas
		row++;
		column = 0;
		ask4Values();
	}
	
	else if(row  ==  size) {
		//Cerramos el scanner ya que en lo que queda de ejecucion del programa no se necesitara leer mas valores por el teclado 
		//Reseteamos los valores auxiliares que actuan como indice de columnas y filas
		 column = 0;
		 row = 0;
	}		 
}

/*
 * Metodo que se encarga unicamente de asignar los valores que va a tener la matriz en cada fila y comprueba
 * tambien si estos valores son validos
 */
public static void setRow(String[] part, int row) {
	
	if(part[iter].equals("0") || part[iter].equals("1") || part[iter].equals("*")) {
		
		if(part[iter].equals("*")) {
			countStar++;
		}
		
		if(countStar > 1){
//Error 3: Mas de 1 asterisco			
			System.err.println("Error: Numero de asteriscos en el input excedido");	
			System.exit(0);
		}
		
		else if(iter < size){
			
		matrix[row][iter] = part[iter]; 
		iter++;
		
			if(iter < size) {
				setRow(part, row);
			}
		}
	}
	else {
//Error 4: Valores diferentes de 1,0 o * introducidos		
		System.err.println("Error: Valores introducidos incorrectos");
		System.exit(0);
	}
}

//Empieza la zona de los algoritmos
//Fin de la clase
}
