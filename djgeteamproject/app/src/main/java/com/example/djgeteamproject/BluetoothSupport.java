package com.example.djgeteamproject;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import java.util.ArrayList;
import java.util.Set;

public class BluetoothSupport {

    public BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    public Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
    public ArrayList mArrayAdapter = new ArrayList();

    public void listPairedDevices() {
        // If there are no paired devices
        if (pairedDevices.size() == 0){
            return;
        }

        // If there are paired devices

    }
}
