package com.example.nekretnine.Model;

public class SearchAdvert {
    private long advertType;
    private long minPrice;
    private long maxPrice;
    private long cityId;
    private long locationId;
    private long numberOfRooms;

    public long getAdvertType() { return advertType; }
    public void setAdvertType(long advertType) { this.advertType = advertType; }

    public long getMinPrice() { return minPrice; }
    public void setMinPrice(long minPrice) { this.minPrice = minPrice; }

    public long getMaxPrice() { return maxPrice; }
    public void setMaxPrice(long maxPrice) { this.maxPrice = maxPrice; }

    public long getCityId() { return cityId; }
    public void setCityId(long cityId) { this.cityId = cityId; }

    public long getLocationId() { return locationId; }
    public void setLocationId(long locationId) { this.locationId = locationId; }

    public long getNumberOfRooms() { return numberOfRooms; }
    public void setNumberOfRooms(long numberOfRooms) { this.numberOfRooms = numberOfRooms; }
}
