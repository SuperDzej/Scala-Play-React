package BLL.Converters

import BLL.Models.SkillModel
import DAL.Models.Skill

object SkillConverter {
  def skillToSkillModel(skill: Skill): SkillModel = {
    SkillModel(Some(skill.id), skill.name, skill.description,
      skill.level)
  }

  def skillModelToSkill(skillModel: SkillModel): Skill = {
    Skill(skillModel.id.getOrElse(0L), name = skillModel.name, description = skillModel.description,
      level = skillModel.level)
  }
}
