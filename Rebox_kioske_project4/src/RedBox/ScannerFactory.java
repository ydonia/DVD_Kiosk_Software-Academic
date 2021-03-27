package RedBox;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

    /**
     * ScannerFactory
     */
    public abstract class ScannerFactory {

        private static Scanner _scannerObject;

        public static Scanner GetScannerInstance()
        {
            if(_scannerObject == null)
            {
                _scannerObject = new Scanner(System.in);
            }

            return _scannerObject;
        }

        // overload the GetScannerInstance
        public static Scanner GetScannerInstance(File file)
        {
            if (_scannerObject == null)
            {
                try{
                    _scannerObject = new Scanner(file);
                }
                catch (FileNotFoundException ex)
                {
                    System.out.println("File not found");
                }

            }

            return _scannerObject;
        }
        public static void CloseScannerInstance()
        {
            _scannerObject.close();
            _scannerObject = null;
        }
    }

