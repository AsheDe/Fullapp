package cu.fullapp;

class ExternalStorageNotAvailableException extends Exception {
    ExternalStorageNotAvailableException() {
        super("No hay almacenamiento externo disponible.");
    }
}