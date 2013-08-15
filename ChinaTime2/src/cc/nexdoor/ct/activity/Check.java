package cc.nexdoor.ct.activity;

import android.app.Activity;
import android.content.Intent;

import net.fet.android.license.sdk.LicenseToolkit;

public class Check extends Activity {

	@Override
	protected void onResume() {
		super.onResume();
		boolean block = LicenseToolkit.BLOCK_MODE;
//		if (LicenseToolkit.acquireClientAndLicense(this, block)) {
			Intent intent = new Intent(this, CTHome.class);
			startActivity(intent);
			finish();
//		}
	}

}
 