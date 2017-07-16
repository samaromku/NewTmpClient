package com.example.andrey.newtmpclient.managers;

import com.example.andrey.newtmpclient.entities.Address;
import com.example.andrey.newtmpclient.utils.RealmInstance;

import java.util.ArrayList;
import java.util.List;

public class AddressManager {
    private Address address;
    private List<Address>addresses;
    private List<Address>usersAddresses;

    public static final AddressManager INSTANCE = new AddressManager();
    private AddressManager(){
        usersAddresses = new ArrayList<>();
        addresses = new ArrayList<>();
    }

    public void addAll(List<Address> addressList){
//        RealmInstance.instance.addAllAddresses(addressList);
        addresses.addAll(addressList);
    }

    public void removeAll(){
        if(addresses.size()>0){
            addresses.clear();
        }
    }

    public List<Address> getUsersAddresses() {
        return usersAddresses;
    }

    public void addAllUserAddresses(List<Address>addresses){
        usersAddresses.clear();
        usersAddresses.addAll(addresses);
    }

    public Address getAddressByName(String name){
        for(Address a:addresses){
            if(a.getName().equals(name)){
                return a;
            }
        }
        return null;
    }

    public Address getAddressByAddress(String address){
        for(Address a:addresses){
            if(a.getAddress().equals(address)){
                return a;
            }
        }
        return null;
    }

    public Address getAddressById(int id){
        for(Address a:addresses){
            if(a.getId()==id){
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

    public Address getAddress() {
        return address;
    }

    public List<Address> getAddresses() {
//        return RealmInstance.instance.getAllAddresses();
        return addresses;
    }
}
