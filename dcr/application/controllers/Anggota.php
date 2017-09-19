<?php
require APPPATH . '/libraries/REST_Controller.php';
class Anggota extends REST_Controller {
  function __construct($config = 'rest') {
      parent::__construct($config);
  }

  function index_get(){
    if($this->get("getBy")){
      $id = array("id_user"=>$this->get("getBy"));
      $this->db->where($id);
      $anggota = $this->db->get('tb_anggota');
      if($anggota->num_rows()>0){
        $this->response($anggota->result(), 200);
      }
      else{
        $data = array("status"=>"data_kosong");
        $this->response(array($data), 200);
      }
    }
  }
  function index_post(){
    if($this->post("simpan")){
      $data = array(
        "id_user"=>$this->post("id_user"),
        "nama_anggota"=>$this->post("nama_anggota"),
        "jabatan"=>$this->post("jabatan"),
        "no_induk"=>$this->post("no_induk")
      );
      $simpan = $this->db->insert("tb_anggota",$data);
      if($simpan){
        $respon = array("status"=>"sukses");
        $this->response(array($respon), 200);
      }
      else{
        $respon = array("status"=>"gagal");
        $this->response(array($respon), 200);
      }
    }
    else{
      $respon = array("status"=>"gagal");
      $this->response(array($respon), 200);
    }
  }
  function index_put(){
    if($this->put("update")){
      $data = array(
        "nama_anggota"=>$this->put("nama_anggota"),
        "jabatan"=>$this->put("jabatan"),
        "no_induk"=>$this->put("no_induk")
      );
      $this->db->where('id_anggota', $this->put("id_anggota"));
      $update = $this->db->update('tb_anggota', $data);
      if ($update) {
        $respon = array("status"=>"sukses");
        $this->response(array($respon), 200);
      } else {
        $respon = array("status"=>"gagal");
        $this->response(array($respon), 200);
      }
    }
    else{
      $respon = array("status"=>"gagal");
      $this->response(array($respon), 200);
    }
  }
}
