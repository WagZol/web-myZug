package com.zoltwagner.myPage.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zoltwagner.myPage.Dao.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	//A Jpa repository-nak megadjuk hogy User entitasokat keresunk és Long tipusu az ID-juk. Mivel a Spring Data Repository automatikusan kigeneralja a
	//lekerdezest nincs szukseg konkret osztaly megalkotasara ami kigeneralja az interface-t.
	User findByUserName(String userName);
	//Csak az elnevezesek alapjan a Spring Data Repository eloallitja azt a lekerdezest amivel user-t lehet keresni email cim alapjan
	//Gondolom bonyolultabb lekerdezesnel mar szukseges a lekerdezes megirasa is
}
