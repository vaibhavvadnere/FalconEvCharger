import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.DecoratedBarcodeView

class BarcodeScannerHandler
    (
    private val context: Context,
    private val barcodeView: DecoratedBarcodeView?,
    private val listener: OnBarcodeScannedListener?
) {

    private val barcodeCallback: BarcodeCallback? = object : BarcodeCallback {
        override fun barcodeResult(result: BarcodeResult) {
            // Check for null result and handle gracefully
            if (result == null) {
                Log.e("BarcodeCallback", "Error: Null barcode result in barcodeResult.")
                return  // Return early if result is null
            }
            val barcodeValue = result.text
            try {
                // Handle the scanned barcode
                listener?.onBarcodeScanned(barcodeValue)
            } catch (e: java.lang.Exception) {
                Log.e("BarcodeCallback", "Error: Exception in handling scanned barcode.", e)
                Toast.makeText(
                    context,
                    "Error processing scanned barcode. Check internet connection.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        override fun possibleResultPoints(resultPoints: List<ResultPoint>) {
            // Optional: handle possible result points
        }
    }

    // Start continuous barcode decoding
    fun startBarcodeScanning() {
        // Validate `barcodeView` and `barcodeCallback`
        if (barcodeView == null) {
            Log.e("BarcodeScannerHandler", "Error: barcodeView is null in startBarcodeScanning.")
            Toast.makeText(
                context,
                "Error: Unable to start scanning. barcodeView is null.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        if (barcodeCallback == null) {
            Log.e(
                "BarcodeScannerHandler",
                "Error: barcodeCallback is null in startBarcodeScanning."
            )
            Toast.makeText(
                context,
                "Error: Unable to start scanning. barcodeCallback is null.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        try {
            barcodeView.decodeContinuous(barcodeCallback)
        } catch (e: Exception) {
            Log.e("BarcodeScannerHandler", "Error: Exception in decodeContinuous", e)
            Toast.makeText(
                context,
                "Error: Unable to start continuous decoding.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    // Pause the scanner when necessary (e.g., in onPause() lifecycle method)
    fun pauseBarcodeScanner() {
        barcodeView?.pause() // Pause the scanner
            ?: Log.e("BarcodeScannerHandler", "Error: barcodeView is null in pauseBarcodeScanner.")
    }

    // Resume the scanner when necessary (e.g., in onResume() lifecycle method)
    fun resumeBarcodeScanner() {
        barcodeView?.resume() // Resume the scanner
            ?: Log.e("BarcodeScannerHandler", "Error: barcodeView is null in resumeBarcodeScanner.")
    }

    // Define the interface for passing the scanned barcode result back to the main activity
    interface OnBarcodeScannedListener {
        fun onBarcodeScanned(barcode: String?)
    }
}
