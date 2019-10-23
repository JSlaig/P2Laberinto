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
		
	if(size > 0) {
		matrix = new String[size][size];
		ask4Values();
		scanner.close();
	}	
	else {
		System.err.println("Error: Tamaño del laberinto invalido.");
	}
}

/*
 * Metodo que usa el scanner para preguntar y asignar los valores de cada posicion de la matriz
*/ 
public static void ask4Values() {
	
	if(column == 0 && row < size) {
		 scanner.nextLine();
		 String value = scanner.nextLine();
		
		 String[] parts = value.split("\\s+");
		 
		 if(parts.length != size) {
			 System.err.println("Entrada inválida");
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
	
	else if( row ==  size) {
		//Cerramos el scanner ya que en lo que queda de ejecucion del programa no se necesitara leer mas valores por el teclado 
		//Reseteamos los valores auxiliares que actuan como indice de columnas y filas
		 column = 0;
		 row = 0;
	}		 
}

/*
 * Metodo que se encarga unicamente de asignar los valores que va a tener la matriz en cada fila
 */
public static void setRow(String[] part, int row) {
	
	//Se produce un error al realizar comprobacion de parametros 
	if(part[iter] == "0" || part[iter] == "1" || part[iter] == "*") {
		if(part[iter] == "*") {
			countStar++;
		}
		if(countStar > 1){
			System.err.println("Error: Numero de asteriscos en el input excedido");	
		}	
		else if(iter < size){
		matrix[row][iter] = part[iter]; 
		iter++;
		setRow(part, row);
		}
	}
	else {
		System.err.println("Error: Valores introducidos incorrectos");
	}
}
}
