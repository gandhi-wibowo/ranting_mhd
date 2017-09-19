<?php
require APPPATH . '/libraries/REST_Controller.php';
class Event extends REST_Controller {
  function __construct($config = 'rest') {
      parent::__construct($config);
  }

  function index_get(){
    if($this->get("One")){
      $data = array("id_event"=>$this->get("One"));
      $this->db->where($data);
      $event = $this->db->get('tb_event');
      if($event->num_rows()>0){
        $data = array();
        foreach ($event->result() as $key => $value) {
          $data['status']="sukses";
          $data['id_event']=$value->id_event;
          $data['id_user']=$value->id_user;
          $data['judul_event']=$value->judul_event;
          $data['keterangan_event']=$value->keterangan_event;
          $data['foto_event']=$value->foto_event;
        }
        $this->response(array($data), 200);
      }
      else{
        $data = array("status"=>"data_kosong");
        $this->response(array($data), 200);
      }
    }
    else if($this->get("All")){
      $event = $this->db->get('tb_event');
      if($event->num_rows()>0){
        $this->response($event->result(), 200);
      }
      else{
        $data = array("status"=>"data_kosong");
        $this->response(array($data), 200);
      }
    }
    else{
      $data = array("status"=>"data_kosong");
      $this->response(array($data), 200);
    }

  }
  function index_put(){
    if($this->put("update")){
      $data = array(
        "judul_event"=>$this->put("judul_event"),
        "keterangan_event"=>$this->put("keterangan_event")
      );
      $this->db->where('id_event', $this->put("id_event"));
      $update = $this->db->update('tb_event', $data);
      if ($update) {
        $respon = array("status"=>"sukses");
        $this->response(array($respon), 200);
      } else {
        $respon = array("status"=>"gagal");
        $this->response(array($respon), 200);
      }
    }
    else {
      $respon = array("status"=>"gagal");
      $this->response(array($respon), 200);
    }
  }
  function index_post(){
    $tanggal = DateTime();
    $data = null;
    if($this->post("image")){
      $image = $this->post("image");
      $date   = date('Ymd His');
      $name   = str_replace(" ","_",$date);
      $path = "img/".$name.".jpg";
      if(file_put_contents($path,base64_decode($image))){
        $data = array(
          "id_user"=>$this->post("id_user"),
          "judul_event"=>$this->post("judul_event"),
          "keterangan_event"=>$this->post("keterangan_event"),
          "foto_event"=>$name.".jpg"
        );
      }
      else{
        $data = array(
          "id_user"=>$this->post("id_user"),
          "judul_event"=>$this->post("judul_event"),
          "keterangan_event"=>$this->post("keterangan_event"),
          "foto_event"=>"no_img.jpg"
        );
      }
    }
    else{
      $data = array(
        "id_user"=>$this->post("id_user"),
        "judul_event"=>$this->post("judul_event"),
        "keterangan_event"=>$this->post("keterangan_event"),
        "foto_event"=>"no_img.jpg"
      );
    }
    $simpan = $this->db->insert("tb_event",$data);
    if($simpan){
      $where = array("id_user"=>$this->post("id_user"));
      $this->db->where($where);
      $users = $this->db->get("tb_users")->row_array();
      if ($users != NULL) {
        $notifikasi = $users['nama_user']." Mengirimkan Event baru";
        $user = $this->db->get("tb_users");
        $select = array(
          "id_user"=>$this->post("id_user"),
          "judul_event"=>$this->post("judul_event")
        );
        $this->db->where($select);
        $event = $this->db->get("tb_event")->row_array();
        foreach ($user->result() as $key => $value) {
          $data = array(
            "id_event"=>$event["id_event"],
            "id_user"=>$value->id_user,
            "isi_notifikasi"=>$notifikasi,
            "tgl_notifikasi"=>$tanggal,
            "status"=>0
          );
          $this->db->insert("tb_notifikasi",$data);
          FCM($value->token,"Event Baru",$notifikasi);
        }
      }
      $respon = array("status"=>"sukses");
      $this->response(array($respon), 200);
    }
    else{
      $respon = array("status"=>"gagal");
      $this->response(array($respon), 200);
    }
  }

}
