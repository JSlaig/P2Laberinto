//fallos que hay que arreglar: se queda petado en las esquinas y lo toma como sin solucion, implementar undo en el resto de metodos 
import java.util.Scanner;

public class MainLaberinto {
	
	static Scanner scanner = new Scanner(System.in);
	
	//Atributos usados en la parte de comprobacion de entradas validas
	static int iter = 0; 
	static int countStar = 0;
	
	//Atributos para definir el laberinto y el movimiento por el
	static int size;
	static int row;
	static int column;	
	static String matrix[][];
	static int ntry = 0;
	
	//Atributos en los cuales se almacenaran las coordenadas de por donde se resuelve el laberinto
	static String coordinateVector[] = new String[100];;
	static int movement = 0;
	static int lastMove = 0;
	static int lastLastMove = 0;

	
//Metodo que se encarga del orden de ejecucion del programa
public static void main(String args[]) {	
	
	System.out.println("Introduzca el tamaño deseado para la matriz:");	
	size = scanner.nextInt();
	scanner.nextLine();
		
	if(size > 0) {
		matrix = new String[size][size];
		ask4Values();
		scanner.close();	
		
		seekPath(6);
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

//zona de algoritmos de resolucion


/*La idea principal es que unas opciones redireccionen a otra para que se sigan comprobando caminos, lo mas complicado sera hacer que recuerde
* el camino seguido para que no se pierda y por lo tanto no entre en un bucle infinito, el registro del camino seguido se podra realizar
* mediante un stringBuffer en el cual se le appendeen las nuevas coordenadas seguidas en cada movimiento y estas sean borradas en el caso de que
* la casilla en la que se llegue sea invalida, una forma posible para poder volver atras y que no se vuelva a meter en el mismo callejon sin salida
* podria ser que fuese cerrando caminos como si dejase migas de pan, es decir cambiara el valor de ese 0 en el cual tiene que retroceder por un 1
* para mostrar que ese camino no tiene salida, lo cual se producira solo cuando no encuentre salida, dejando asi libre el unico camino donde puede
* avanzar
*/

//Switch recursivo que llama a los movimientos
public static void seekPath(int i) {
	
	while(row < (size - 1) && column < (size - 1) && row >= 0 && column >= 0) {
		switch(i) {
		//Caso 1: Avanza hacia la derecha
		case 6:
			goRight(6);
		break;
		
		//Caso 2: Avanza hacia la izquierda
		case 4:
			goLeft(4);
		break;
		
		//Caso 3: Avanza hacia abajo
		case 2:
			goDown(2);
		break;
		
		//Caso 4: Avanza hacia arriba
		case 8:
			goUp(8);
		break;
		
		//Caso 5: Avanza en diagonal hacia abajo derecha
		case 3:
			goDownRight(3);
		break;
		
		//Caso 6: Avanza en diagonal hacia arriba derecha
		case 9:
			goUpRight(9);
		break;
		
		//Caso 7: Avanza en diagonal hacia abajo a la izquierda
		case 1:
			goDownLeft(1);
		break;
		
		//Caso 8: Avanza en diagonal hacia arriba a la izquierda
		case 7:
			goUpLeft(7);
		break;
		
		default:
			System.out.println("NO.");
			System.exit(0);
		break;
		}
	}
	if(column == (size - 1) && row == (size - 1)) {
		printWinCoordinates();
	}
	else if () {
		
	}
	else {
		System.out.println("NO.");
		System.exit(0);
	}
}


/*
 * Funcion que comprueba si el ultimo movimiento realizado te deja en una posicion valida es valida
 */
public static boolean possibleMove(int moveID) {
	if(matrix[row][column].equals("1") == true || lastMove == moveID || lastLastMove == moveID && lastMove != 0) {
		return false;
	}
	else 
	{
		return true;
	}
}
//Hay que iplementar que en el ultimo movimiento posible se cambie la posicion actual por un 1 para cerrar el camino
//Rama de movimientos posibles
public static void goRight(int moveID) {
	//Movimiento
	column++;
	
	//Comprobacion
	if(possibleMove(moveID) == false) {
		column--;
		ntry++;
		seekPath(3);
	}
	else{			
			ntry = 0;
			
			lastLastMove = lastMove;
			lastMove = 6;
			writeCoordinate();
			movement++;
			seekPath(6);
	}
}
public static void goLeft(int moveID) {
	//Movimiento
	column--;
	
	//Comprobacion
	if(possibleMove(moveID) == false) {
		column++;
		ntry++;
		seekPath(7);
	}
	else {		
		ntry = 0;
		
		lastLastMove = lastMove;
		lastMove = 4;
		writeCoordinate();
		movement++;
		seekPath(4);
	}
}
public static void goDown(int moveID) {
	//Movimiento
	row++;
	
	//Comprobacion
	if(possibleMove(moveID) == false) {
		row--;
		ntry++;
		seekPath(1);
	}
	else {		
		ntry = 0;
		
		lastLastMove = lastMove;
		lastMove = 2;
		writeCoordinate();
		movement++;
		seekPath(2);
	}
}
public static void goUp(int moveID) {
	//Movimiento
	row--;
	
	//Comprobacion
	if(possibleMove(moveID) == false) {
		row++;
		ntry++;
		seekPath(9);
	}
	else {		
		ntry = 0;
		
		lastLastMove = lastMove;
		lastMove = 8;
		writeCoordinate();
		movement++;
		seekPath(8);
	}
}
public static void goDownRight(int moveID) {
	//Movimiento
	column++;
	row++;
	
	//Comprobacion
	if(possibleMove(moveID) == false) {
		column--;
		row--;
		ntry++;
		
		seekPath(2);
	}
	else {		
		ntry = 0;
		
		lastLastMove = lastMove;
		lastMove = 3;
		writeCoordinate();
		movement++;
		seekPath(3);
	}
}
public static void goUpRight(int moveID) {
	//Movimiento
	column++;
	row--;
	
	//Comprobacion
	if(possibleMove(moveID) == false) {
		column--;
		row++;
		ntry++;
		
		seekPath(4);
	}
	else {		
		ntry = 0;
		
		lastLastMove = lastMove;
		lastMove = 9;
		writeCoordinate();
		movement++;
		seekPath(9);
	}
}
public static void goUpLeft(int moveID) {
	//Movimiento
	column--;
	row--;
	
	//Comprobacion
	if(possibleMove(moveID) == false) {
		column++;
		row++;
		ntry++;
		
		seekPath(8);
	}
	else {	
		ntry = 0;
		
		lastLastMove = lastMove;
		lastMove = 7;
		writeCoordinate();
		movement++;
		seekPath(7);
	}
}
public static void goDownLeft(int moveID) {
	//Movimiento
	column--;
	row++;
	
	//Comprobacion
	if(possibleMove(moveID) == false) {
		column++;
		row--;
		ntry++;
		seekPath(4);
	}
	else {		
		ntry = 0;
		
		lastLastMove = lastMove;
		lastMove = 1;
		writeCoordinate();
		movement++;
		seekPath(1);
	}
}

public static void undo() {
	if(ntry == 9) {
		switch(lastMove) {
		case 6:
			removeCoordinate();
			matrix[row][column] = "1";
			movement--;
			
			column--;
		break;
		
		case 4:
			removeCoordinate();
			matrix[row][column] = "1";
			movement--;
			
			column++;
		break;
		
		case 2:
			removeCoordinate();
			matrix[row][column] = "1";
			movement--;
			
			row--;
		break;
		
		case 8:
			removeCoordinate();
			matrix[row][column] = "1";
			movement--;
			
			row++;
		break;
		
		case 3:
			removeCoordinate();
			matrix[row][column] = "1";
			movement--;
			
			column--;
			row--;
		break;
		
		case 1:
			removeCoordinate();
			matrix[row][column] = "1";
			movement--;
			
			column++;
			row--;
		break;
		
		case 7:
			removeCoordinate();
			matrix[row][column] = "1";
			movement--;
			
			column++;
			row++;
		break;
		
		case 9:
			removeCoordinate();
			matrix[row][column] = "1";
			movement--;
			
			column--;
			row++;
		break;
		
		default:
			System.exit(0);
		break;
		}
	}
	seekPath(lastMove);
}

//Rama de coordenadas del programa
public static void writeCoordinate(){
		if(matrix[row][column].equals("*") == true) {
			coordinateVector[movement] = "("+row+","+column+")* ";
		}else {
			coordinateVector[movement] = "("+row+","+column+") ";
		}
	}

public static void removeCoordinate() {
	coordinateVector[movement] = null;
}

public static void printWinCoordinates() {
	int i = 0;
	StringBuffer winResult = new StringBuffer();
	if(matrix[0][0].equals("*") == true) {
		winResult.append("(0,0)* ");
	}else {
		winResult.append("(0,0) ");
	}
	while(coordinateVector[i] != null) {
		winResult.append(coordinateVector[i]);
		i++;
	}
	
	if(winResult.toString().contains("*") == true) {
		System.out.println("SI, CON PREMIO.");
		System.out.println(winResult.toString());
		System.exit(0);
	}
	else{
		System.out.println("SI, SIN PREMIO.");
		System.out.println(winResult.toString());
		System.exit(0);
	}
}

//Fin de la clase
}
