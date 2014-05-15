package com.e1858.wuye.entity.hibernate;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;


@Entity
@Table(name = "t_BbsBoard", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class BbsBoard extends BaseEntity
{

	private Long id;
	private SysUser sysUser;
	private SysCorp sysCorp;
	private Community community;
	private String name;
	private String description;
	private long topics;
	private Date createTime;
	private Date cancelTime;
	private Long canceler;
	private Date disableTime;
	private Long disabler;

	public BbsBoard()
	{
	}

	public BbsBoard(SysUser sysUser, SysCorp sysCorp, Community community, String name, long topics, Date createTime)
	{
		this.sysUser = sysUser;
		this.sysCorp = sysCorp;
		this.community = community;
		this.name = name;
		this.topics = topics;
		this.createTime = createTime;
	}

	public BbsBoard(SysUser sysUser, SysCorp sysCorp, Community community, String name, String description, long topics, Date createTime, Date cancelTime, Long canceler, Date disableTime, Long disabler)
	{
		this.sysUser = sysUser;
		this.sysCorp = sysCorp;
		this.community = community;
		this.name = name;
		this.description = description;
		this.topics = topics;
		this.createTime = createTime;
		this.cancelTime = cancelTime;
		this.canceler = canceler;
		this.disableTime = disableTime;
		this.disabler = disabler;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId()
	{
		return this.id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "creator", nullable = false)
	public SysUser getSysUser()
	{
		return this.sysUser;
	}

	public void setSysUser(SysUser sysUser)
	{
		this.sysUser = sysUser;
	}

	@ManyToOne
	@JoinColumn(name = "corp", nullable = false)
	public SysCorp getSysCorp()
	{
		return this.sysCorp;
	}

	public void setSysCorp(SysCorp sysCorp)
	{
		this.sysCorp = sysCorp;
	}

	@ManyToOne
	@JoinColumn(name = "community", nullable = false)
	public Community getCommunity()
	{
		return this.community;
	}

	public void setCommunity(Community community)
	{
		this.community = community;
	}

	@Column(name = "name", unique = true, nullable = false, length = 150)
	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Column(name = "description", length = 256)
	public String getDescription()
	{
		return this.description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	@Column(name = "topics", nullable = false)
	public long getTopics()
	{
		return this.topics;
	}

	public void setTopics(long topics)
	{
		this.topics = topics;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "createTime", nullable = false, length = 10)
	public Date getCreateTime()
	{
		return this.createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "cancelTime", length = 10)
	public Date getCancelTime()
	{
		return this.cancelTime;
	}

	public void setCancelTime(Date cancelTime)
	{
		this.cancelTime = cancelTime;
	}

	@Column(name = "canceler")
	public Long getCanceler()
	{
		return this.canceler;
	}

	public void setCanceler(Long canceler)
	{
		this.canceler = canceler;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "disableTime", length = 10)
	public Date getDisableTime()
	{
		return this.disableTime;
	}

	public void setDisableTime(Date disableTime)
	{
		this.disableTime = disableTime;
	}

	@Column(name = "disabler")
	public Long getDisabler()
	{
		return this.disabler;
	}

	public void setDisabler(Long disabler)
	{
		this.disabler = disabler;
	}
}
