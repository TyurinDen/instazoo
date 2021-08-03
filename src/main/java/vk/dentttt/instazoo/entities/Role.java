package vk.dentttt.instazoo.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "roles")
@Data
@EqualsAndHashCode(exclude = {"users"}, callSuper = false)
@ToString(exclude = {"users"})
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "roleBuilder")
public class Role extends BaseEntity {

    @Column(unique = true, nullable = false, length = 30, updatable = false)
    private String name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<User> users;

}
