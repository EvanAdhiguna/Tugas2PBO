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
Mendapatkan seluruh record customer </br>
http://localhost:9139/customers
<img src = "Image/getCustomer.jpeg">

Mendapatkan record customer dengan ID customer adalah 2 </br>
http://localhost:9139/customers/2
<img src = "Image/getCustomerId.jpeg">

Mendapatkan record cards dengan ID Customer adalah </br>
http://localhost:9139/customers/2/cards
<img src = "Image/getCustomersIdCards.jpeg">

Mendapatkan record customer subscription record berdasarkan ID customer adalah 2 </br>
http://localhost:9052/customers/2/subscriptions
<img src = "Image/GETCustomerIdSubs.jpeg">

Mendapatkan seluruh record item </br>
http://localhost:9139/items
<img src = "Image/GETitems.jpeg">

Mendapatkan record item dengan ID item adalah 1 </br>
http://localhost:9139/items/1
<img src = "Image/GETitems_id.jpeg">

Mendapatkan record item dengan status active adalah true </br>
http://localhost:9139/items?is_active=true
<img src = "Image/GETitemsis_active=true.jpeg">

Mendapatkan record semua subscription </br>
http://localhost:9139/subscriptions
<img src = "Image/GETsubscriptions.jpeg">

Mendapatkan record subscription dengan current term end descending </br>
http://localhost:9139/subscriptions?sort_by=current_term_end&sort_type=desc
<img src = "Image/GETsubscriptionsdesc.jpeg">

##
### POST
Menambahkan data baru pada Customers </br>
http://localhost:9139/customers
<img src = "Image/POSTcustomers.jpeg">

Menambahkan data baru pada Items </br>
http://localhost:9139/items
<img src = "Image/POST items.jpeg">

Menambahkan data baru pada Subscriptions </br>
http://localhost:9139/subscriptions
<img src = "Image/postSubscription.jpeg">
<br/>


##
### PUT
Mengupdate data Customers </br>
http://localhost:9139/customers/7
<img src = "Image/putCustomerId.jpeg">

Mengupdate data Items </br>
http://localhost:9139/items/2
<img src = "Image/PUT items_id.jpeg">

##
### DELETE
Menghapus atau mengubah status is_active menjadi false pada Items berdasarkan ID </br>
http://localhost:9139/items/2
<img src = "Image/deleteItemsId.jpeg">

Menghapus informasi kartu kredit customer jika is_primary bernilai false </br>
http://localhost:9139/customer/5/cards/5
<img src = "Image/deleteCustomerCard.jpeg">

##
### ERROR 404
http://localhost:9139/roger/2 (Tidak ada entitas ROGER)
<img src = "Image/ERROR404.jpeg">

### ERROR 405
PATCH http://localhost:9139 (Di luar GET, POST, PUT, DELETE)
<img src = "Image/ERROR405.jpeg">
