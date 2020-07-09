package com.zoltwagner.myPage.Repository;

import com.zoltwagner.myPage.Dao.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	//A Jpa repository-nak megadjuk hogy User entitasokat keresunk és Long tipusu az ID-juk. Mivel a Spring Data Repository automatikusan kigeneralja a
	//lekerdezest nincs szukseg konkret osztaly megalkotasara ami kigeneralja az interface-t.
	Role findByName(String name);
	//Csak az elnevezesek alapjan a Spring Data Repository eloallitja azt a lekerdezest amivel user-t lehet keresni email cim alapjan
	//Gondolom bonyolultabb lekerdezesnel mar szukseges a lekerdezes megirasa is
}
