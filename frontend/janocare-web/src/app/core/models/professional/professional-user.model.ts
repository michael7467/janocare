import { ProfessionalUserStatus, ResourceModel } from '../../../shared';
import { User } from '../user';
import { ProfessionType } from './profession-type.model';
import { ProfessionalInfo } from './professional-info.model';
import { ProfessionalReview } from './professional-review.model';
import { ProfessionalQualification } from './professional-qualification.model';
import { ProfessionalMembership } from './professional-membership.model';
import { SpecializationModel } from '../specialization';
import { ProfessionalExperience } from './professional-experience.model';
import { ProfessionalService } from './professional-service.model';
import { ProfessionalRegistration } from './professional-registration.model';
import { ProfessionalAchievement } from './professional-achievement.model';
// import { PostModel } from '../blog';
import { BookingSlot } from '../booking';

export class ProfessionalModel extends ResourceModel<ProfessionalModel> {
  practicingFrom: Date;
  consultationFee: number;
  bookingFee: number;
  instantConsultationFee: number;
  upVotes: number;
  downVotes: number;
  viewCounts: number;
  bio?: string;
  status?: ProfessionalUserStatus;
  walletBalance: number;
  user: User;
  professionType?: ProfessionType;
  professionTypeId: number;
  specializations: SpecializationModel[];
  professionalQualifications?: ProfessionalQualification[];
  professionalExperiences?: ProfessionalExperience[];
  professionalServices?: ProfessionalService[];
  professionalMemberships?: ProfessionalMembership[];
  professionalAchievements?: ProfessionalAchievement[];
  professionalRegistrations?: ProfessionalRegistration[];
  professionalReviews?: ProfessionalReview[];
  professionalInfos?: ProfessionalInfo[];
  bookingSlots?: BookingSlot[];
  // posts?: Post[];
overallRatings?: number;
  totalReviews?: number;
  totalPatients?: number;
  isOnlineConsultationEnabled?: boolean;
  isInpersonEnabled?: boolean;
  isInstantCallEnabled?: boolean;
  constructor(model?: Partial<ProfessionalModel>) {
    super(model);
  }
}
