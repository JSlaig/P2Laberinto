import java.util.Scanner;

public class MainLaberinto {
	
	static Scanner scanner = new Scanner(System.in);
	
	static int iter = 0; 
	static int countStar = 0;
	
	static int size;
	static int row;
	static int column;
	
	static String matrix[][];
	static String coordinateVector[] = new String[size * size];
	static StringBuffer coordinates = new StringBuffer();
	
	static int movement = 0;
	
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


/*La idea principal es que unas opciones redireccionen a otra para que se sigan comprobando caminos, lo mas complicado sera hacer que recuerde
* el camino seguido para que no se pierda y por lo tanto no entre en un bucle infinito, el registro del camino seguido se podra realizar
* mediante un stringBuffer en el cual se le appendeen las nuevas coordenadas seguidas en cada movimiento y estas sean borradas en el caso de que
* la casilla en la que se llegue sea invalida, una forma posible para poder volver atras y que no se vuelva a meter en el mismo callejon sin salida
* podria ser que fuese cerrando caminos como si dejase migas de pan, es decir cambiara el valor de ese 0 en el cual tiene que retroceder por un 1
* para mostrar que ese camino no tiene salida, lo cual se producira solo cuando no encuentre salida, dejando asi libre el unico camino donde puede
* avanzar
*/
public static void seekPath(int i) {
	i = 6;
	while(row < (size - 1) && column < (size - 1)) {
		switch(i) {
		//Caso 1: Avanza hacia la derecha
		case 6:
			goRight();
		break;
		
		//Caso 2: Avanza hacia la izquierda
		case 4:
			goLeft();
		break;
		
		//Caso 3: Avanza hacia abajo
		case 2:
			goDown();
		break;
		
		//Caso 4: Avanza hacia arriba
		case 8:
			goUp();
		break;
		
		//Caso 5: Avanza en diagonal hacia abajo derecha
		case 3:
			goDownRight();
		break;
		
		//Caso 6: Avanza en diagonal hacia arriba derecha
		case 9:
			goUpRight();
		break;
		
		//Caso 7: Avanza en diagonal hacia abajo a la izquierda
		case 1:
			goDownLeft();
		break;
		
		//Caso 8: Avanza en diagonal hacia arriba a la izquierda
		case 7:
			goUpLeft();
		break;
		
		default:
			//Retroceder y cerrar camino
		break;
		}
	}		
}


/*
 * Funcion que comprueba si el ultimo movimiento realizado te deja en una posicion valida es valida
 */
public static boolean possibleMove() {
	if(matrix[row][column].equals("1") == true) {
		return false;
	}
	else 
	{
		return true;
	}
}
//Hay que iplementar que en el ultimo movimiento posible se cambie la posicion actual por un 1 para cerrar el camino
public static void goRight() {
	//Movimiento
	column++;
	
	//Comprobacion
	if(possibleMove() == false) {
		column--;
		seekPath(3);
	}
	else{
			movement++;
			writeCoordinate(row, column);
			seekPath(6);
	}
}

public static void goLeft() {
	//Movimiento
	column--;
	
	//Comprobacion
	if(possibleMove() == false) {
		column++;
		seekPath(7);
	}
	else {
		writeCoordinate(row, column);
		seekPath(3);
	}
}

public static void goDown() {
	//Movimiento
	row++;
	
	//Comprobacion
	if(possibleMove() == false) {
		row--;
		seekPath(1);
	}
	else {
		writeCoordinate(row, column);
		seekPath(6);
	}
}
public static void goUp() {
	//Movimiento
	row--;
	
	//Comprobacion
	if(possibleMove() == false) {
		row++;
		seekPath(9);
	}
	else {
		writeCoordinate(row, column);
		seekPath(6);
	}
}

public static void goDownRight() {
	//Movimiento
	column++;
	row++;
	
	//Comprobacion
	if(possibleMove() == false) {
		column--;
		row--;
		seekPath(2);
	}
	else {
		writeCoordinate(row, column);
		seekPath(6);
	}
}

public static void goUpRight() {
	//Movimiento
	column++;
	row--;
	
	//Comprobacion
	if(possibleMove() == false) {
		column--;
		row++;
		seekPath(4);
	}
	else {
		writeCoordinate(row, column);
		seekPath(6);
	}
}

public static void goUpLeft() {
	//Movimiento
	column--;
	row--;
	
	//Comprobacion
	if(possibleMove() == false) {
		column++;
		row++;
		seekPath(4);
	}
	else {
		writeCoordinate(row, column);
		seekPath(6);
	}
}

public static void goDownLeft() {
	//Movimiento
	column--;
	row++;
	
	//Comprobacion
	if(possibleMove() == false) {
		column++;
		row--;
		seekPath(7);
	}
	else {
		writeCoordinate(row, column);
		seekPath(6);
	}
}
public static void writeCoordinate(int x, int y){
		if(matrix[x][y].equals("*") == true) {
			coordinates.append("("+x+","+y+")* ");
		}else {
			coordinates.append("("+x+","+y+") ");
		}
	}
//Fin de la clase
}
