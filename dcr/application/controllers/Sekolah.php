<?php

require APPPATH . '/libraries/REST_Controller.php';
class Sekolah extends REST_Controller {
  function __construct($config = 'rest') {
      parent::__construct($config);
  }

  function index_get(){
    if($this->get("One")){
      $id = array("id_sekolah"=>$this->get("One"));
      $this->db->where($id);
      $sekolah = $this->db->get('tb_sekolah');
      if($sekolah->num_rows()>0){
        $this->response($sekolah->result(), 200);
      }
      else{
        $data = array("status"=>"data_kosong");
        $this->response(array($data), 200);
      }
    }
    else if($this->get("getBy")){
      $id = array("id_user"=>$this->get("getBy"));
      $this->db->where($id);
      $this->db->order_by("nama_sekolah");
      $sekolah = $this->db->get('tb_sekolah');
      if($sekolah->num_rows()>0){
        $this->response($sekolah->result(), 200);
      }
      else{
        $data = array("status"=>"data_kosong");
        $this->response(array($data), 200);
      }
    }
    else{
      $data = array("status"=>"gagal");
      $this->response(array($data), 200);
    }
  }
  function index_post(){
    $image = $this->post("image");
    if($image != NULL){
      $image = $this->post("image");
      $date   = date('Ymd His');
      $name   = str_replace(" ","_",$date);
      $path = "img/".$name.".jpg";
      if(file_put_contents($path,base64_decode($image))){
        $data = array(
          "id_user"=>$this->post("id_user"),
          "nama_sekolah"=>$this->post("nama_sekolah"),
          "alamat_sekolah"=>$this->post("alamat_sekolah"),
          "foto_sekolah"=>$name.".jpg"
        );
      }
    }
    else{
      $data = array(
        "id_user"=>$this->post("id_user"),
        "nama_sekolah"=>$this->post("nama_sekolah"),
        "alamat_sekolah"=>$this->post("alamat_sekolah"),
        "foto_sekolah"=>"no_img.jpg"
      );
    }
    $simpan = $this->db->insert("tb_sekolah",$data);
    if($simpan){
      $respon = array("status"=>"sukses");
      $this->response(array($respon), 200);
    }
    else{
      $respon = array("status"=>"gagal");
      $this->response(array($respon), 200);
    }
  }
  function index_put(){
    $idSekolah = $this->put("id_sekolah");
    if($this->put("latLong")){
      $latLong = array(
        "lat"=>$this->put("lat"),
        "lng"=>$this->put("lng")
      );
      $this->db->where('id_sekolah', $idSekolah);
      $update = $this->db->update('tb_sekolah', $latLong);
      if ($update) {
        $respon = array("status"=>"sukses");
        $this->response(array($respon), 200);
      } else {
        $respon = array("status"=>"gagal");
        $this->response(array($respon), 200);
      }
    }
    if($this->put("sekolah")){
      if($this->put("image")){
        $image = $this->put("image");
        $date   = date('Ymd His');
        $name   = str_replace(" ","_",$date);
        $path = "img/".$name.".jpg";
        if(file_put_contents($path,base64_decode($image))){
          $sekolah = array(
            "nama_sekolah"=>$this->put("nama_sekolah"),
            "alamat_sekolah"=>$this->put("alamat_sekolah"),
            "foto_sekolah"=>$name.".jpg"
          );
        }
      }
      else{
        $sekolah = array(
          "nama_sekolah"=>$this->put("nama_sekolah"),
          "alamat_sekolah"=>$this->put("alamat_sekolah")
        );
      }

      $this->db->where('id_sekolah', $idSekolah);
      $update = $this->db->update('tb_sekolah', $sekolah);
      if ($update) {
        $respon = array("status"=>"sukses");
        $this->response(array($respon), 200);
      } else {
        $respon = array("status"=>"gagal");
        $this->response(array($respon), 200);
      }
    }
  }
}
