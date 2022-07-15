<?php
  class Move {
    private $conn;
    private $table = 'moves';

    public $id;
    public $order;
    public $move;
    public $id_game;

    public function __construct($db) {
      $this->conn = $db;
    }

  public function read_byGame(){
    $query = 
        'SELECT
            id,
            order,
            move
        FROM'
            . $this->table .
            'WHERE id_game = id_game';

      $stmt = $this->conn->prepare($query);
      $stmt -> bindParam(':id_game', $this->id);
      $stmt -> execute();

      return $stmt
  }

  public function create() {
    $query = 
    'INSERT INTO ' 
        . $this->table . 
    'SET
        order = :order,
        move = :move,
        id_game = :id_game';

  $stmt = $this->conn->prepare($query);

  $this->order = htmlspecialchars(strip_tags($this->order));
  $this->move = htmlspecialchars(strip_tags($this->move));
  $this->id_game = htmlspecialchars(strip_tags($this->id_game));

  $stmt-> bindParam(':order', $this->order);
  $stmt-> bindParam(':move', $this->move);
  $stmt-> bindParam(':id_game', $this->id_game);

  if($stmt->execute()) {
    return true;
  }

  printf("Error: $s.\n", $stmt->error);

  return false;
  }

  public function update() {
    $query = 
    'UPDATE ' 
    . $this->table . 
    'SET
        order = :order,
        move = :move,
        id_game = :id_game
    WHERE id = :id';

  $stmt = $this->conn->prepare($query);

  $this->order = htmlspecialchars(strip_tags($this->order));
  $this->move = htmlspecialchars(strip_tags($this->move));
  $this->id_game = htmlspecialchars(strip_tags($this->id_game));
  $this->id = htmlspecialchars(strip_tags($this->id));

  $stmt-> bindParam(':order', $this->order);
  $stmt-> bindParam(':move', $this->move);
  $stmt-> bindParam(':id_game', $this->id_game);
  $stmt->bindParam(':id', $this->id);

  if($stmt->execute()) {
    return true;
  }

  printf("Error: $s.\n", $stmt->error);

  return false;
  }

  public function delete() {
    $query = 
    'DELETE FROM ' 
        . $this->table . 
    ' WHERE 
        id = :id';

    $stmt = $this->conn->prepare($query);

    $this->id = htmlspecialchars(strip_tags($this->id));

    $stmt-> bindParam(':id', $this->id);

    if($stmt->execute()) {
      return true;
    }

    printf("Error: $s.\n", $stmt->error);

    return false;
    }
  }