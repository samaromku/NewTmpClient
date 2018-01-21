package com.example.andrey.newtmpclient.managers;

import com.example.andrey.newtmpclient.entities.Address;

import java.util.ArrayList;
import java.util.List;

public class AddressManager {
    private List<Address>addresses;
    private List<Address>usersAddresses;

    public static final AddressManager INSTANCE = new AddressManager();
    private AddressManager(){
        usersAddresses = new ArrayList<>();
        addresses = new ArrayList<>();
    }

    public void addAll(List<Address> addressList){
        addresses.addAll(addressList);
    }

    public void removeAll(){
        if(addresses.size()>0){
            addresses.clear();
        }
    }

    public void addAllUserAddresses(List<Address>addresses){
        usersAddresses.clear();
        usersAddresses.addAll(addresses);
    }

    public Address getAddressByAddress(String address){
        for(Address a:addresses){
            if(a.getAddress().equals(address)){
                return a;
            }
        }
        return null;
    }

    public Address getUserAddressById(int id){
        for(Address a:usersAddresses){
            if(a.getId()==id){
                return a;
            }
        }
        return null;
    }

    public List<Address> getAddresses() {
        return addresses;
    }
}
