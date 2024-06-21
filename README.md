I Gde Evan Adhiguna  2305551082 <br>
I Made Tara Bujawan  2305551139 <br>

# <p align="center">**Sistem Pembayaran Subscription**<p>
### <p align="center">API Java Sederhana</p>

## Pengenalan
Proyek ini merupakan suatu backend API sederhana yang dibuat dengan Java dengan Maven, yang mana disusun untuk sistem pembayaran subscription sederhana. API digunakan untuk mengakses dan melakukan manipulasi data pada tiap entitas dari database dan dapat menangani berbagai jenis permintaan GET, POST, PUT, DELETE. Semua respon yang diberikan oleh server API menggunakan format **JSON**, dan data disimpan dalam database **SQLite**. Untuk memastikan fungsi API berjalan dengan baik, pengujian dilakukan menggunakan aplikasi **Postman**.

## Struktur Program
Program ini memiliki 3 tipe class, yaitu class untuk masing-masing Entitas yang terletak pada folder **sketch**, class untuk keperluan API dan HTTP Server pada folder **server**, dan class untuk keperluan database pada folder **persistence**.

## Test pada Postman
Program ini pada dasarnya dapat diakses melalui **localhost:9139** menggunakan web browser. Namun, untuk mempermudah pengujian dan memastikan berbagai fungsi API bekerja dengan benar, aplikasi ini diuji menggunakan Postman. 

### GET
Mendapatkan seluruh record item </br>
http://localhost:9139/items
<img src = "Image/GETitems.jpeg">

Mendapatkan seluruh record customer </br>
http://localhost:9139/customers
<img src = "Image/getCustomer.jpeg">

Mendapatkan record customer dengan ID customer adalah 2 </br>
http://localhost:9139/customers/2
<img src = "Image/getCustomerId.jpeg">

Mendapatkan record item dengan ID item adalah 1 </br>
http://localhost:9139/items/1
<img src = "Image/GETitems_id.jpeg">

Mendapatkan record semua subscription </br>
http://localhost:9139/subscriptions
<img src = "Image/GETsubscription.jpeg">

Mendapatkan record customer's subscription record berdasarkan ID customer adalah 1 </br>
http://localhost:9139/customers/1/subscriptions?subscriptions_status=active
<img src = "Image/getCustomerIdSubscription.jpeg">













### POST


### PUT


### DELETE
