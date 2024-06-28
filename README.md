APLIKASI DATABASE CLIENT

Aplikasi yang dapat digunakan untuk terkoneksi dengan server basis data. Setelah terkoneksi aplikasi dapat menampilkan list basis data yang tersimpan pada server tersebut serta dapat melakukan operasi SQL dan menampilkan hasilnya.
1. Aplikasi menampilkan halaman koneksi basis data
Koneksi basis data biasanya memerlukan inputan host, username, password, dan port.
Setelah berhasil melakukan koneksi, aplikasi akan menampilkan halaman utama.
2. Halaman utama aplikasi terdiri dari 3 panel:
Panel Database Navigator
Panel Query
dan Panel hasil
3. Panel Database Navigator menampilkan List Database dan Struktur Tabel yang ada di dalamnya.
Panel ini dapat ditampilkan dalam bentuk TreeView dengan default belum mengakes basis data tertentu sehingga tampilannya masih collapsed (tidak menampilkan list tabel pada database tersebut)
Double Klik pada Database akan mentrigger akses ke database tersebut serta menampilkan semua tabel yang ada di dalam database tersebut.
Klik pada Tabel akan otomatis mengisikan query select * from {nama_tabel} limit 100 pada panel Query sekaligus mengeksekusinya.
4. Panel Query untuk menampilkan dan mengedit teks query yang akan dieksekusi serta tombol untuk mengeksekusi query tersebut.
Hasil eksekusi query akan ditampilkan pada panel Hasil.
{opsional} akan lebih baik jika panel query menggunakan text editor yang dilengkapi dengan indentasi dan syntax highlight.
Jika querynya adalah query DDL (data definition language), maka hasil query-nya bisa langsung terefleksi di Panel Database Navigator. Misalkan penambahan tabel akan otomatis muncul, dan penghapusan tabel juga akan otomatis menghilangkan data tabelnya di Database Navigator.
5. Panel Hasil untuk menampilkan hasil query baik itu query select maupun query yang lain.
Hasil dari query select ditampilkan dalam bentuk tabel.
Isi tabel hasil tersebut tidak dapat diedit tapi dapat di-copy nilainya.
Hasil dari query lainnya insert, update, create table, dll bisa ditampilkan dalam bentuk teks saja.
Jika terdapat error pada query, error tersebut juga dapat ditampilkan dalam bentuk teks pada panel hasil ini.
